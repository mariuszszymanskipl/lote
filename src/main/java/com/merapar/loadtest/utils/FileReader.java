package com.merapar.loadtest.utils;

import com.merapar.loadtest.web.controller.Image;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileReader {

    @Value("${imagesFolder:images}")
    private String imagesFolder;

    @Value("${jMeterTestsFolder:jMeterTests}")
    private String jMeterTestsFolder;

    public static List<String> getJMeterTests() {
        List<String> tests = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("jMeterTests").getFileName())) {
            paths.filter(Files::isRegularFile).forEach(path -> tests.add(getFileNameWithExtensionFromPath(path.toString())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tests;
    }

    public static List<Image> getImages() {
        List<Image> images = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("images").getFileName())) {
            paths.filter(Files::isRegularFile).forEach(path -> images.add(new Image(getFileNameWithExtensionFromPath(path.toString()))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return images;
    }

    private void createImagesFolder() {
        File dir = new File(imagesFolder);
        if (!dir.exists())
            dir.mkdirs();
    }

    private static String getFileNameWithExtensionFromPath(String path) {
        return path.contains(File.separator) ? path.substring(path.lastIndexOf(File.separator) + 1) : path;
    }
}
