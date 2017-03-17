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
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);


        new getHTML().execute(link);


    }

    public class getHTML extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String loi = "<center>" + getString(R.string.connection_issue) + "</center>";

            Document doc = null;
            Element e = null;

            try {
                doc = Jsoup.connect(params[0]).get();
            } catch (Exception ex) {
                System.out.println(ex);
            }

            if (doc != null) {
                try {
                    e = doc.select("div#container").first();
                    e.html();
                } catch (Exception ex) {
                    try {
                        e = doc.select("div#video_left").first();
                    } catch (Exception eee) {
                        return loi;
                    }
                }
            } else {
                return loi;
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
            } catch (Error | Exception er) {
                System.out.println(er);
            }

        }

    }
}
