package com.konyang.springbootservice2.domain.posts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface HtmlFileService {
    void createTable(HtmlFile htmlFile);

    void saveInputData(HtmlFile htmlFile, String inputName, String inputValue);

    BufferedImage takeScreenshot(HtmlFile htmlFile) throws AWTException, IOException;
}

/*

-> Former 'PostsRepository' interface code

package com.konyang.springbootservice2.web;

import com.konyang.springbootservice2.domain.posts.HtmlFile;
import com.konyang.springbootservice2.domain.posts.HtmlFileService;
import com.konyang.springbootservice2.web.dto.HtmlFileDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
            // Create a File object with the desired file path
            File dest = new File(filename);
            // Save the file to the desired directory using transferTo() method
            file.transferTo(dest);

            // Scan the uploaded HTML file and count the number of input tags
            Document doc = Jsoup.parse(file.getInputStream(), "UTF-8", "");
            Elements inputElements = doc.select("input");
            int numInputs = inputElements.size();

            // Create a new HtmlFile object with the uploaded file name and input count
            HtmlFile htmlFile = new HtmlFile(filename, numInputs);

            // Create a new database table with a name based on the uploaded file name
            htmlFileService.createTable(htmlFile);

            // Take a screenshot of the entire screen and save it to disk
            BufferedImage screenshot = htmlFileService.takeScreenshot(htmlFile);

            // Create a new HtmlFileDto object based on the HtmlFile entity
            HtmlFileDto htmlFileDto = new HtmlFileDto(htmlFile.getName(), htmlFile.getInputCount());

            // Add the HtmlFileDto object to the model for rendering on the index page
            model.addAttribute("htmlFileDto", htmlFileDto);

            // Add the screenshot filename to the model for rendering on the index page
            String screenshotFilename = "screenshot_" + filename.replace(".html", ".png");
            model.addAttribute("screenshot", screenshotFilename);

            return "index";
        } else {
            model.addAttribute("error", "Invalid file type. Please upload an HTML file.");
            return "error";
        }
    }


    @PostMapping("/update")
    @ResponseBody
    public String handleInputUpdate(@RequestParam("tableName") String tableName, @RequestParam("inputName") String inputName, @RequestParam("inputValue") String inputValue) {
        HtmlFile htmlFile = new HtmlFile(tableName + ".html", 0);
        htmlFileService.saveInputData(htmlFile, inputName, inputValue);
        return "Success";
    }
}
 */