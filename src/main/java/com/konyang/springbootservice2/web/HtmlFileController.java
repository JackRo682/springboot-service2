package com.konyang.springbootservice2.web;

import com.konyang.springbootservice2.domain.posts.HtmlFile;
import com.konyang.springbootservice2.domain.posts.HtmlFileService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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

public class HtmlFileController {
    @Autowired
    private HtmlFileService htmlFileService;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException, AWTException {
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);

        if (extension.equals("html")) {



            /* HTML file saving */
            // Create a File object with the desired file path
            File dest = new File("C:\\Users\\Jack Ro\\Desktop\\freelec-springboot2-webservice-master\\testdb\\" + file.getOriginalFilename());
            File dest = new File(filename);

            // Save the file to the desired directory using transferTo() method
            file.transferTo(dest);



            /* HTML file input tag count & database creation */
            // Scan the uploaded HTML file and count the number of input tags
            Document doc = Jsoup.parse(file.getInputStream(), "UTF-8", "");
            Elements inputElements = doc.select("input");
            int numInputs = inputElements.size();

            // Create a new HtmlFile object with the uploaded file name and input count
            HtmlFile htmlFile = new HtmlFile(filename, numInputs);

            // Create a new database table with a name based on the uploaded file name
            htmlFileService.createTable(htmlFile);



            /* HTML file screenshot save */
            // Define the file path where you want to save the screenshot
            String screenshotFilename = "screenshot_" + filename.replace(".html", ".png");
            String screenshotPath = "C:\\Users\\Jack Ro\\Desktop\\캡스톤 디자인 2\\Screenshot" + screenshotFilename;

            // Take a screenshot of the entire screen and save it to disk
            BufferedImage screenshot = htmlFileService.takeScreenshot(htmlFile);

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