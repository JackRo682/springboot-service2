package com.konyang.springbootservice2.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

@RestController
public class FileUploadController {
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
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

        return "File uploaded successfully! Number of input tags: " + numInputs;
    }
}