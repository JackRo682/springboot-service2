package com.konyang.springbootservice2.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

@RestController
public class FileUploadController {
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



        return "File uploaded successfully! Number of input tags: " + numInputs;
    }
}