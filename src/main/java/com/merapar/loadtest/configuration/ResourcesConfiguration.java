package com.merapar.loadtest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class ResourcesConfiguration {

    @Value("${imagesFolder:images}")
    private String imagesFolder;

    @Value("${jMeterTestsFolder:jMeterTests}")
    private String jMeterTestsFolder;

    @Value("${maxWidth}")
    private Integer maxWidth;

    @Value("${maxHeight}")
    private Integer maxHeight;


    public String getImagesFolder() {
        return imagesFolder;
    }

    public String getJMeterTestsFolder() {
        return jMeterTestsFolder;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }


}
