package com.example.brom.listviewjsonapp;

public class Mountain {
    private int id;
    private String name;
    private int height;
    private String location;
    private String imgURL;
    private String articleURL;

   public Mountain(int id, String name, int height, String location, String imgURL, String articleURL) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.location = location;
        this.imgURL = imgURL;
        this.articleURL = articleURL;
    }

    public Mountain(int id, String name, int height) {
        this.id = id;
        this.name = name;
        this.height = height;
        location = "Unknown";
        imgURL = "No image";
        articleURL = "No article";
    }

    public Mountain() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public String getInfo() {
        return name + " is located in " + location + " and is " + height + "m high.";
    }
}
