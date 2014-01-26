package com.jml.example.app.data;

/**
 * Created by jose on 26/01/2014.
 *
 * @author jose
 * @version 0.1.0
 * @since 1
 */
public class ItemRow {

    /**
     * URL link to where image
     * is placed in a server
     */
    private String url;

    /**
     * Text description
     */
    private String text;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
