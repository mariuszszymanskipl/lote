package com.merapar.loadtest.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class ImageConfiguration {

    @Value("${imagesFolder:images}")
    private String imagesFolder;

    @Value("${maxWidth}")
    private Integer maxWidth;

    @Value("${maxHeight}")
    private Integer maxHeight;


    public String getImagesFolder() {
        return imagesFolder;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public Integer getMaxHeight() {
        return maxHeight;
    }


}
