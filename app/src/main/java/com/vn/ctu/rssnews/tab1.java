package com.vn.ctu.rssnews;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Scanner;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class tab1 extends Fragment {


    ListView listView;
    TextView textView, textView_er;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    Animation open, close;
    ArrayList<RssItem> list;
    Location location;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab1, container, false);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));

        textView = (TextView) root.findViewById(R.id.textView19);
        textView_er = (TextView) root.findViewById(R.id.textView_er);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        listView = (ListView) root.findViewById(R.id.listView);
        relativeLayout = (RelativeLayout) root.findViewById(R.id.relativeLayout);
        open = AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_in_bottom);
        close = AnimationUtils.loadAnimation(getActivity(), R.anim.abc_slide_out_bottom);

        textView.setText(MainActivity.chude);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new layThongTinThoiTiet().execute(location.getLatitude() + "", location.getLongitude() + "");
                relativeLayout.startAnimation(close);
                relativeLayout.setVisibility(View.GONE);
                textView_er.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });

        new layThongTinThoiTiet().execute(location.getLatitude() + "", location.getLongitude() + "");

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    public class getRss extends AsyncTask<String, Void, myHandler> {

        @Override
        protected myHandler doInBackground(String... params) {
            try {

                URL url = new URL(params[0]);
                System.out.println(url.toString());
                URLConnection conn = url.openConnection();
                conn.setRequestProperty(
                        "User-Agent",
                        "Mozilla/5.0 (Macintosh; U; "
                                + "Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                SAXParserFactory fac = SAXParserFactory.newInstance();
                SAXParser paser = fac.newSAXParser();
                myHandler myHandler = new myHandler(list);
                paser.parse(new InputSource(conn.getInputStream()), myHandler);

                return myHandler;
            } catch (UnknownHostException ue) {
                System.out.println("Loi mang: " + ue.toString());
                return null;
            } catch (Exception e) {
                System.out.println("Loi lay rss: " + e.toString());
                return null;
            } catch (Error er) {
                System.out.println("Loi lay rss: " + er.toString());
                return null;
            }
        }

        @Override
        protected void onPostExecute(final myHandler result) {
            super.onPostExecute(result);
            if (result != null) {
                myListAdapter listAdapter = new myListAdapter(getActivity().getApplicationContext(), result.rss);

                listView.setAdapter(listAdapter);
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                relativeLayout.startAnimation(open);
                relativeLayout.setVisibility(View.VISIBLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tab2.webView.setVisibility(View.GONE);
                        tab2.webView.loadData("", "text/html; charset=utf-8", "UTF-8");
                        new getHTML().execute(result.rss.get(position).getLink());
                        System.out.println(result.rss.get(position).getLink());
                        MainActivity.viewPager.setCurrentItem(1);
                    }
                });
                new getHTML().execute(list.get(0).getLink());
            } else {
                Toast.makeText(getActivity(), "Không thể kết nối!, vui lòng kiểm tra mạng", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                textView_er.setVisibility(View.VISIBLE);
                relativeLayout.startAnimation(open);
                relativeLayout.setVisibility(View.VISIBLE);

            }


            //


        }

    }

    public class getHTML extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Document doc = null;
            Element e = null;
            //String html = new String();
            System.out.println(params[0]);
            if (params[0].equalsIgnoreCase("")) {
                return list.get(0).getSummary();
            }
            try {

                doc = Jsoup.connect(params[0]).get();
                e = doc.select("div#container").first();
                e.html();

            } catch (Exception ex) {
                System.out.println("Loi lay HTML: " + ex.toString());
                try {
                    e = doc.select("div#video_left").first();
                } catch (Error eee) {
                    return "Lỗi! Không thể hiển thị trang web. Xin thử lại.";
                }
            }
            try {
                e.select("a").remove();
                e.select("div.timer_header").remove();
                e.select("div.block_timer_share").remove();
                e.select("div#social_like").remove();
                e.select("div#box_tinkhac_detail").remove();
                e.select("div#box_comment").remove();
                e.select("div#right_calculator").remove();
                e.select("div.right").remove();
                e.select("div.block_tag").remove();
                e.select("div.relative_new").remove();
                e.select("div#box_tinlienquan").remove();
                e.select("div.right_lienquan").remove();
            } catch (Error ee) {
            } catch (Exception eee) {
            }

            return e.html();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                tab2.webView.setVisibility(View.VISIBLE);
                tab2.webView.loadData(result, "text/html; charset=utf-8", "UTF-8");
            } catch (Error er) {
            } catch (Exception e) {
            }

        }

    }


    class layThongTinThoiTiet extends AsyncTask<String, Integer, String> {

        URL url = null;
        String wurl = "http://api.openweathermap.org/data/2.5/weather?lat=";

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(wurl + params[0] + "&lon=" + params[1]);
                System.out.println(url.toString());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                //Ket noi
                connection.connect();

                //Đọc dữ liệu
                InputStream inputStream = connection.getInputStream();
                Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");

                String kq = scanner.hasNext() ? scanner.next() : "";
                inputStream.close();


                JSONObject jsonObject = new JSONObject(kq);
                String name = jsonObject.getString("name");
                String nuoc = jsonObject.getJSONObject("sys").getString("country");
                System.out.println(name + ", " + nuoc);
                String tt = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");

                JSONObject main = jsonObject.getJSONObject("main");
                Double nhietdo = main.getDouble("temp") - 273.15;
                Double nhietdocaonhat = main.getDouble("temp_max") - 273.15;
                Double nhietdothapnhat = main.getDouble("temp_min") - 273.15;
                String doam = main.getString("humidity") + "%";

                RssItem item = new RssItem();
                item.setTitle(name + ", " + nuoc);
                item.setSummary(tt + ". Nhiệt độ: " + nhietdo.toString().substring(0, 6) + ", cao nhất: " + nhietdocaonhat.toString().substring(0, 6) + ", thấp nhất: " + nhietdothapnhat.toString().substring(0, 6) + ". Độ ẩm: " + doam);

                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int gio = calendar.get(Calendar.HOUR_OF_DAY);
                int phut = calendar.get(Calendar.MINUTE);

                item.setPubDate(year + ", " + month + ", " + day + ", " + gio + ", " + phut);
                //item.setLink("http://api.openweathermap.org/data/2.5/weather?q=" + name.replace(" ", "+") + "&mode=html");
                item.setLink("");
                System.out.println("http://api.openweathermap.org/data/2.5/weather?q=" + name.replace(" ", "+") + "&mode=html");
                item.setImageURL(myHandler.getBitmapFromURL("http://openweathermap.org/img/w/" + jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon") + ".png"));
                list = new ArrayList<>();

                list.add(item);

                System.out.println("Da them");


                return null;
            } catch (Exception e) {

            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            new getRss().execute(MainActivity.Rssurl);


        }
    }


}
