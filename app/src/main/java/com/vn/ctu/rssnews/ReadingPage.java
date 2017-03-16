package com.vn.ctu.rssnews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ReadingPage extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_page);

        getSupportActionBar().hide();

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        webView = (WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webView.loadUrl(link);
        new getHTML().execute(link);


    }

    public class getHTML extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Document doc = null;
            Element e = null;
            //String html = new String();
            System.out.println(params[0]);

            try {

                doc = Jsoup.connect(params[0]).get();
                e = doc.select("div#container").first();
                e.html();

            } catch (Exception ex) {
                System.out.println("Loi lay HTML: " + ex.toString());
                try {
                    e = doc.select("div#video_left").first();
                } catch (Exception eee) {
                    return "<center>Vui lòng kiểm tra kết nối và thử lại</center>";
                }
            }
            try {
                e.select("a").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div.timer_header").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div.block_timer_share").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div#social_like").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div#box_tinkhac_detail").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div#box_comment").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div#right_calculator").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div.right").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div.block_tag").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div.relative_new").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div#box_tinlienquan").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div.right_lienquan").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }
            try {
                e.select("div.xemthem_new_ver").remove();
            } catch (Exception ee) {
                System.out.println(ee);
            }

            return e.html();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                String css = "<style type='text/css'>img {height: auto ;max-width: 100%;}</style>";
                webView.setVisibility(View.VISIBLE);
                webView.loadData(css + result, "text/html; charset=utf-8", "UTF-8");
            } catch (Error er) {
            } catch (Exception e) {
            }

        }

    }
}
