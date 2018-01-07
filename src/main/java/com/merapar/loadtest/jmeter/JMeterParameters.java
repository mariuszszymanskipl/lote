package com.merapar.loadtest.jmeter;

public class JMeterParameters {

    private String jMeterTest;
    private String imageName;
    private String numberOfUsers;
    private String duration;

    public String getjMeterTest() {
        return jMeterTest;
    }

    public void setjMeterTest(String jMeterTest) {
        this.jMeterTest = jMeterTest;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
