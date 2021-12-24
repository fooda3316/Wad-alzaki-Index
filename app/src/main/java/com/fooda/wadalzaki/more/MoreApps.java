package com.fooda.wadalzaki.more;

public class MoreApps {
    private String title;
    private String uri;
    private int image;

    public MoreApps(String title, String uri, int image) {
        this.title = title;
        this.uri = uri;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public int getImage() {
        return image;
    }
}
