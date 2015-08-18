package com.vn.ctu.rssnews;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class myHandler extends DefaultHandler {
    public ArrayList<RssItem> rss;
    RssItem rssitem;
    boolean itemfound;
    String temp;
    private StringBuilder text;

    public myHandler(ArrayList<RssItem> list) {
        rss = list;
        this.text = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // TODO Auto-generated method stub
        super.startElement(uri, localName, qName, attributes);

        //temp = new StringBuilder();
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
                        rssitem.title = temp;
                    } else {
                        if (qName.equalsIgnoreCase("link")) {
                            rssitem.link = temp;
                        } else {
                            if (qName.equalsIgnoreCase("pubDate")) {
                                rssitem.pubDate = temp.replace("+0700", "");
                            } else {
                                if (qName.equalsIgnoreCase("description")) {
                                    //rssitem.summary = temp;

                                    Document doc = Jsoup.parse(temp);
                                    String imgURL = "";
                                    try {
                                        imgURL = doc.select("img").first().attr("src");
                                    } catch (Exception e) {
                                        System.out.println("LOI LAY URL: " + e.toString() + " " + temp);
                                        rssitem.image = null;
                                    }
                                    System.out.println(imgURL);
                                    rssitem.image = getBitmapFromURL(imgURL);

                                    rssitem.summary = temp.substring(temp.indexOf("</a></br>") + 9);


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

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);

        //temp = new String(ch, start, length);
        this.text.append(ch, start, length);
        temp = text.toString();
        System.out.println(temp);

    }

    // download hinh va tao lai doi tuong bitmap

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

}
