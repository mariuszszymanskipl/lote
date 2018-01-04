package com.merapar.loadtest;

import com.merapar.loadtest.web.controller.Image;

import java.util.List;

public class JMeterParameters {

    private String UsersNumber;
    private String Duration;
    private List<Image> images;


    public String getUsersNumber() {
        return UsersNumber;
    }

    public void setUsersNumber(String usersNumber) {
        UsersNumber = usersNumber;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
