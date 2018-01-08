package com.merapar.loadtest.web.controller;

import com.merapar.loadtest.configuration.ResourcesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class InputResourceController {

    private static final String IMAGE_PNG_CONTENT_TYPE = "image/png";
    private static final String IMAGE_JPEG_CONTENT_TYPE = "image/jpeg";

    @Autowired
    private ResourcesConfiguration resourcesConfiguration;

    private Logger logger = LoggerFactory.getLogger(InputResourceController.class);

    @RequestMapping("/image")
    public String welcome() {
        return "image-upload";
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

                logger.info("Server File Location=" + serverFile.getAbsolutePath());

                String message = "You successfully uploaded new file: " + image.getName();
                model.addAttribute("message", message);
                return "upload-confirm";

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
                File dir = new File(resourcesConfiguration.getjMeterTestsFolder());
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




//    private static long uploadSingleImage(String folder, MultipartFile item, Long uploadSizeLimitInBytes) {
//
//        String contentType = item.getContentType();
//
//        if (contentType == null) {
//            return 0;
//        }
//
//        if (contentType.equals(IMAGE_PNG_CONTENT_TYPE) || contentType.equals(IMAGE_JPEG_CONTENT_TYPE)) {
//            try {
//                String imageNameWithExtension = item.getOriginalFilename();
//                LimitedSizeInputStream limitedSizeInputStream = new LimitedSizeInputStream (((BodyPartEntity) item.getEntity()).getInputStream(), uploadSizeLimitInBytes);
//
//                byte[] imageBytes = IOUtils.toByteArray(limitedSizeInputStream);
//                long bytesUploaded = limitedSizeInputStream.getTotal();
//
//                if (ImageDataStorage.getRawImageStorage().writeImage(folder, imageNameWithExtension, imageBytes)){
//                    ImageDataStorage.clearCachedFiles(folder, imageNameWithExtension);
//                }
//                return bytesUploaded;
//            } catch (IOException e) {
//                throw new ImageFileUploadException(e);
//            }
//        } else {
//            throw new ImageFileEncodingUnsupportedException("Unsupported Image file encoding uploaded. File %s has content type %s",
//                    item.getFormDataContentDisposition().getFileName(), item.getMediaType());
//        }
}
