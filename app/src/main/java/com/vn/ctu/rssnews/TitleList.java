package com.vn.ctu.rssnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.xml.sax.InputSource;

import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class TitleList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String Rssurl = "http://vnexpress.net/rss/tin-moi-nhat.rss";
    public static String chude = "Tin mới nhất";
    ListView listView;
    ProgressBar progressBar;
    FloatingActionButton refresh_button;
    Animation open, close;
    ArrayList<RssItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(chude);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listView);
        refresh_button = (FloatingActionButton) findViewById(R.id.refresh_button);
        open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_in_bottom);
        close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.abc_slide_out_bottom);


        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getRss().execute(Rssurl);
                refresh_button.startAnimation(close);
                refresh_button.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        });

        new getRss().execute(Rssurl);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.title_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.tinmoi: {
                Rssurl = "http://vnexpress.net/rss/tin-moi-nhat.rss";
                chude = getString(R.string.tinmoi);
                break;
            }
            case R.id.thoisu: {
                Rssurl = "http://vnexpress.net/rss/thoi-su.rss";
                chude = getString(R.string.thoisu);
                break;
            }
            case R.id.thegioi: {
                Rssurl = "http://vnexpress.net/rss/the-gioi.rss";
                chude = getString(R.string.thegioi);
                break;
            }
            case R.id.thethao: {
                Rssurl = "http://vnexpress.net/rss/the-thao.rss";
                chude =  getString(R.string.thethao);
                break;
            }
            case R.id.phapluat: {
                Rssurl = "http://vnexpress.net/rss/phap-luat.rss";
                chude =  getString(R.string.phapluat);
                break;
            }
            case R.id.giaoduc: {
                Rssurl = "http://vnexpress.net/rss/giao-duc.rss";
                chude =  getString(R.string.giaoduc);
                break;
            }
            case R.id.suckhoe: {
                Rssurl = "http://vnexpress.net/rss/suc-khoe.rss";
                chude =  getString(R.string.suckhoe);
                break;
            }
            case R.id.doisong: {
                Rssurl = "http://vnexpress.net/rss/doi-song.rss";
                chude =  getString(R.string.doisong);
                break;
            }
            case R.id.dulich: {
                Rssurl = "http://vnexpress.net/rss/du-lich.rss";
                chude =  getString(R.string.dulich);
                break;
            }
            case R.id.khoahoc: {
                Rssurl = "http://vnexpress.net/rss/khoa-hoc.rss";
                chude =  getString(R.string.khoahoc);
                break;
            }
            default:
                return true;

        }

        new getRss().execute(Rssurl);
        refresh_button.startAnimation(close);
        refresh_button.setVisibility(View.GONE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        setTitle(chude);
        drawer.closeDrawer(GravityCompat.START);
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);


        return true;
    }

    public class getRss extends AsyncTask<String, Void, myHandler> {

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

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
                list = new ArrayList<>();
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
                myListAdapter listAdapter = new myListAdapter(getApplicationContext(), result.rss);

                listView.setAdapter(listAdapter);
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                refresh_button.startAnimation(open);
                refresh_button.setVisibility(View.VISIBLE);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        System.out.println(result.rss.get(position).getLink());
                        Intent intent = new Intent(getApplicationContext(), ReadingPage.class);
                        intent.putExtra("link", result.rss.get(position).getLink());
                        startActivity(intent);


                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.connection_issue), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                refresh_button.startAnimation(open);
                refresh_button.setVisibility(View.VISIBLE);

            }

        }

    }
}
