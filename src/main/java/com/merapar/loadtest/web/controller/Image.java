package com.merapar.loadtest.web.controller;

public class Image {

    private String name;
    private String path;

    public Image() {
    }

    public Image(String path) {
        this.path = path;
        this.name = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
