package com.konyang.springbootservice2.web;

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
        // Extract the file's extension
        String filename = file.getOriginalFilename();
        String extension = filename.substring(filename.lastIndexOf(".") + 1);

        if (extension.equals("html")) {

            /* HTML file save to the local folder */
            // Create a File object with the desired file path
            File dest = new File("C:\\Users\\Jack Ro\\Desktop\\freelec-springboot2-webservice-master\\testdb\\" + file.getOriginalFilename());

            // Save the file to the desired directory using transferTo() method
            file.transferTo(dest);

/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class dFileUploadController {

    // Use dependency injection to get a reference to the JdbcTemplate object
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Handle file uploads with a POST request to the /upload endpoint
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) throws IOException {

        // Create a File object with the desired file path
        File dest = new File("C:\\Users\\Jack Ro\\Desktop\\freelec-springboot2-webservice-master\\testdb\\" + file.getOriginalFilename());

        // Save the file to the desired directory using transferTo() method
        file.transferTo(dest);
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
*/

            /* HTML file screenshot save */
            // Define the file path where you want to save the screenshot
            String screenshotFilename = "screenshot_" + filename.replace(".html", ".png");
            String screenshotPath = "C:\\Users\\Jack Ro\\Desktop\\캡스톤 디자인 2\\Screenshot" + screenshotFilename;

            // Create an HtmlFile object
            HtmlFile htmlFile = new HtmlFile(filename, 0);

            // Take a screenshot of the entire screen and save it to disk
            BufferedImage screenshot = htmlFileService.takeScreenshot(htmlFile);

            // Write the screenshot to the file
            try {
                ImageIO.write(screenshot, "png", new File(screenshotPath));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "redirect:/index";
/*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // Take a screenshot of the entire screen
        BufferedImage screenshot = null;
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            screenshot = robot.createScreenCapture(screenRect);
        } catch (Exception e) {
            System.out.println("Error taking screenshot: " + e.getMessage());
        }

        // Save the screenshot to a file
        if (screenshot != null) {
            String screenshotFilename = "screenshot_" + file.getOriginalFilename().replace(".html", ".png");
            File screenshotFile = new File("C:\\Users\\Jack Ro\\Desktop\\freelec-springboot2-webservice-master\\testdb\\" + screenshotFilename);
            ImageIO.write(screenshot, "png", screenshotFile);

            // Add the screenshot filename to the model for rendering on the index page
            model.addAttribute("screenshot", screenshotFilename);
        }
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
*/

            /* HTML file input tag count & database creation */
            // Scan the uploaded HTML file and count the number of input tags
            Document doc = Jsoup.parse(file.getInputStream(), "UTF-8", "");
            Elements inputElements = doc.select("input");
            int numInputs = inputElements.size();

            // Create a new HtmlFile object with the uploaded file name and input count
            HtmlFile htmlFile = new HtmlFile(filename, numInputs);

            // Create a new database table with a name based on the uploaded file name
            htmlFileService.createTable(htmlFile);

        } else {
            model.addAttribute("error", "Invalid file type. Please upload an HTML file.");
            return "error";
        }
    }
}