package com.merapar.loadtest.jmeter;

public class JMeterParameters {

    private String test;
    private String numberOfUsers;
    private String duration;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(String numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
