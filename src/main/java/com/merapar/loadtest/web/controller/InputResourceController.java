package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.configuration.ResourcesConfiguration;
import com.merapar.loadtest.jmeter.JMeterParameters;
import com.merapar.loadtest.utils.FileReader;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.List;


@Controller
public class InputResourceController {

    private static final String IMAGE_PNG_CONTENT_TYPE = "image/png";
    private static final String IMAGE_JPEG_CONTENT_TYPE = "image/jpeg";

    @Autowired
    private ResourcesConfiguration resourcesConfiguration;

    private Logger logger = LoggerFactory.getLogger(InputResourceController.class);

    @RequestMapping("/image")
    public String welcome(Model model) {
        List<Image> images = FileReader.getImages();
        JMeterParameters param = new JMeterParameters();
        model.addAttribute("param", param);
        model.addAttribute("images", images);
        return "image-upload";
    }

    @ResponseBody
    @RequestMapping(path = "/img", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@RequestParam("imageName") String imageName) {

        return getImageBytes(imageName);
    }

    private byte[] getImageBytes(String imageName) {

        byte[] imageBytes = new byte[]{};

        File dir = new File(resourcesConfiguration.getImagesFolder());
        if (!dir.exists())
            dir.mkdirs();

        File imageFile = new File(dir.getAbsolutePath() + File.separator + imageName);

        InputStream imageStream;
        try {
            imageStream = new BufferedInputStream(new FileInputStream(imageFile));
            imageBytes = IOUtils.toByteArray(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageBytes;

    }

    @RequestMapping(value = "/uploadSingleImage", method = RequestMethod.POST)
    public String uploadFileHandler(Model model,
                                                  @RequestParam("name") String name,
                                                  @RequestParam("file") MultipartFile file) {

        String item = file.getContentType();

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                File dir = new File(resourcesConfiguration.getImagesFolder());
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                // Create image object
                Image image = new Image();
                image.setName(name);
                image.setPath(serverFile.getAbsolutePath());

                logger.info("Image File location on server = " + serverFile.getAbsolutePath());

                String message = "You successfully uploaded new file: " + image.getName();
                model.addAttribute("message", message);
                return this.welcome(model);

            } catch (Exception e) {
                String message = "You failed to upload " + name + " => " + e.getMessage();
                model.addAttribute("message", message);
                return "upload-confirm";
            }
        } else {
            String message = "You failed to upload " + name + " because the file was empty.";
            model.addAttribute("message", message);
            return "upload-confirm";
        }
    }

    @RequestMapping("/jMeterTest")
    public String jMeterTest() {
        return "jMeterTest-upload";
    }

    @RequestMapping(value = "/uploadJMeterTest", method = RequestMethod.POST)
    public String uploadJMeterTestHandler(Model model,
                                          @RequestParam("name") String testName,
                                          @RequestParam("file") MultipartFile file) {

        String item = file.getContentType();

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                File dir = new File(resourcesConfiguration.getJMeterTestsFolder());
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + testName);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));

                stream.write(bytes);
                stream.close();

                logger.info("Server File Location=" + serverFile.getAbsolutePath());

                String message = "You successfully uploaded new file: " + testName;
                model.addAttribute("message", message);
                return "upload-confirm";

            } catch (Exception e) {
                String message = "You failed to upload " + testName + " => " + e.getMessage();
                model.addAttribute("message", message);
                return "upload-confirm";
            }
        } else {
            String message = "You failed to upload " + testName + " because the file was empty.";
            model.addAttribute("message", message);
            return "upload-confirm";
        }
    }
}
