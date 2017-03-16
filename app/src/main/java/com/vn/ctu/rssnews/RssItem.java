package com.vn.ctu.rssnews;

import android.graphics.Bitmap;

public class RssItem {
    private String title;
    private String link;
    private String pubDate;
    private String summary;
    private String imgUrl;

    public RssItem() {
        super();
    }

    public RssItem(String title, String link, String pubDate, String summary,
                   Bitmap image) {
        super();
        this.title = title;
        this.link = link;
        this.pubDate = pubDate;
        this.summary = summary;
    }

    public String getTitle() {
        return title.replaceAll("\n", "").trim();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link.replaceAll("\n", "").replaceAll(" ", "");
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate.replaceAll("\n", "").trim();
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSummary() {
        return summary.replaceAll("\n", "").trim();
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}


