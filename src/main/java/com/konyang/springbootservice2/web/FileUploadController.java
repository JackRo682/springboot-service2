package com.konyang.springbootservice2.web;// Import the necessary libraries
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

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

        // Scan the uploaded HTML file and count the number of input tags
        Document doc = Jsoup.parse(file.getInputStream(), "UTF-8", "");
        Elements inputElements = doc.select("input");
        List<Element> inputList = new ArrayList<>(inputElements);
        int numInputs = inputList.size();

        // Create a new database table with a name based on the uploaded file name
        String tableName = file.getOriginalFilename().replace(".html", "");
        jdbcTemplate.execute("CREATE TABLE " + tableName + " (id INT AUTO_INCREMENT PRIMARY KEY)");

        // Add columns for each input tag
        for (int i = 0; i < numInputs; i++) {
            String columnName = "input_" + i;
            jdbcTemplate.execute("ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " VARCHAR(255)");
        }

        // Get the input values and insert them into the database table
        for (Element input : inputList) {
            String inputName = input.attr("name");
            String inputValue = input.attr("value");

            String query = "INSERT INTO " + tableName + " (" + inputName + ") VALUES ('" + inputValue + "')";
            jdbcTemplate.execute(query);
        }

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

        // Add the database table name and number of inputs to the model for rendering on the index page
        model.addAttribute("tableName", tableName);
        model.addAttribute("numInputs", numInputs);

        return "index";
    }

    // Return the name of the view to be rendered (in this case, "index.html")
    @PostMapping("/update")
    @ResponseBody
    public String handleInputUpdate(@RequestParam("tableName") String tableName, @RequestParam("inputName") String inputName, @RequestParam("inputValue") String inputValue) {
        // Update the corresponding database entry with the new input value
        String query = "UPDATE " + tableName + " SET " + inputName + " = '" + inputValue + "'";
        jdbcTemplate.execute(query);

        return "Success";
    }
}