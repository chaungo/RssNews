package com.vn.ctu.rssnews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class myHandler extends DefaultHandler {
    ArrayList<RssItem> rss;
    private RssItem rssitem;
    private boolean itemfound;
    private String temp;
    private StringBuilder text;

    public myHandler(ArrayList<RssItem> list) {
        rss = list;
        this.text = new StringBuilder();
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            connection.disconnect();

            return myBitmap;
        } catch (Exception e) {
            System.out.println("LOI GETBITMAP: " + e.toString());
            return null;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        temp = "";
        if (qName.equalsIgnoreCase("item")) {
            rssitem = new RssItem();
            itemfound = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        try {
            if (!qName.equalsIgnoreCase("item")) {
                if (itemfound) {
                    if (qName.equalsIgnoreCase("title")) {
                        rssitem.setTitle(temp);
                    } else {
                        if (qName.equalsIgnoreCase("link")) {
                            rssitem.setLink(temp);
                        } else {
                            if (qName.equalsIgnoreCase("pubDate")) {
                                rssitem.setPubDate(temp.replace("+0700", ""));
                            } else {
                                if (qName.equalsIgnoreCase("description")) {
                                    //rssitem.summary = temp;

                                    Document doc = Jsoup.parse(temp);
                                    String imgURL = "";
                                    try {
                                        imgURL = doc.select("img").first().attr("src");
                                    } catch (Exception e) {
                                        System.out.println("LOI LAY URL: " + e.toString() + " " + temp);

                                    }
                                    //System.out.println(imgURL);
                                    rssitem.setImgUrl(imgURL);

                                    rssitem.setSummary(temp.substring(temp.indexOf("</a></br>") + 9));


                                }
                            }
                        }
                    }
                }
            } else {
                rss.add(rssitem);
                rssitem = null;
                itemfound = false;
            }
        } catch (Exception e) {
            System.out.println("LOI HANDLE: " + e.toString());
        }

        this.text.setLength(0);
    }

    // download hinh va tao lai doi tuong bitmap

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);

        //temp = new String(ch, start, length);
        this.text.append(ch, start, length);
        temp = text.toString();
        //System.out.println(temp);

    }

}
