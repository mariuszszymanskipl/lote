package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.jmeter.JMeterParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class WelcomeController {

    @Value("${imagesFolder:images}")
    private String imagesFolder;

    @RequestMapping("/")
    public String welcome(Model model) {

        JMeterParameters param = new JMeterParameters();

        createImagesFolder();
        List<Image> images = getImages();
        model.addAttribute("param", param);
        model.addAttribute("images", images);

        return "welcome";
    }

    private List<Image> getImages() {
        List<Image> images = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("images").getFileName())) {
            paths.filter(Files::isRegularFile).forEach(path -> images.add(new Image(getImageNameWithExtensionFromPath(path.toString()))));
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

    private String getImageNameWithExtensionFromPath(String path) {
        return path.contains(File.separator) ? path.substring(path.lastIndexOf(File.separator) + 1) : path;
    }

}