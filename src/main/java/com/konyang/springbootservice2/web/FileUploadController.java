package com.konyang.springbootservice2.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.io.*;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        // Create a File object with the desired file path
        File dest = new File("C:\\Users\\Jack Ro\\Desktop\\freelec-springboot2-webservice-master\\testdb\\" + file.getOriginalFilename());

        // Save the file to the desired directory using transferTo() method
        file.transferTo(dest);

        return "File uploaded successfully!";
    }
}