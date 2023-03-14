package com.konyang.springbootservice2.web;

import com.konyang.springbootservice2.web.dto.HtmlFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Controller

public class FileUploadController {
    @Autowired
    private com.konyang.springbootservice2.domain.posts.HtmlFileInterface HtmlFileInterface;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException, AWTException {
        // Extract the file's extension
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);

        if (extension.equals("html")) {


            /* HTML file save to the local folder */
            // Create a File object with the desired file path
            File dest = new File("C:\\Users\\Jack Ro\\Desktop\\freelec-springboot2-webservice-master\\testdb\\" + file.getOriginalFilename());

            // Save the file to the desired directory using transferTo() method
            file.transferTo(dest);


            /* HTML file screenshot save */
            // Define the file path where you want to save the screenshot
            String screenshotFilename = "screenshot_" + filename.replace(".html", ".png");
            String screenshotPath = "C:\\Users\\Jack Ro\\Desktop\\캡스톤 디자인 2\\Screenshot" + screenshotFilename;

            // Create an HtmlFile object
            HtmlFileDto htmlFile = new HtmlFileDto(filename, 0);

            // Take a screenshot of the entire screen and save it to disk
            BufferedImage screenshot = HtmlFileInterface.takeScreenshot(htmlFile);

            // Write the screenshot to the file
            try {
                ImageIO.write(screenshot, "png", new File(screenshotPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Invalid file type. Please upload an HTML file.");
            return "error";
        }
    }
}