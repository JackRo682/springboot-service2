package com.konyang.springbootservice2.service.posts;

import com.konyang.springbootservice2.domain.posts.HtmlFileInterface;
import com.konyang.springbootservice2.web.dto.HtmlFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HtmlFileService implements HtmlFileInterface {
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(HtmlFileService.class);
    private JdbcTemplate jdbcTemplate;


    @Override
    public void createTable(HtmlFile htmlFile) {
        String tableName = htmlFile.getName().replace(".html", "");

        try {
            // parse the HTML file using JSoup
            File input = new File(htmlFile.getName());
            Document doc = Jsoup.parse(input, "UTF-8");
            Elements inputElements = doc.getElementsByTag("input");

            // create a list of column names based on the names of the input fields
            List<String> columnNames = new ArrayList<String>();
            for (Element inputElement : inputElements) {
                String name = inputElement.attr("name");
                columnNames.add(name);
            }

            // create a SQL query to create the table with the correct column names
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE ").append(tableName).append(" (id INT AUTO_INCREMENT PRIMARY KEY");
            for (String columnName : columnNames) {
                sb.append(", ").append(columnName).append(" VARCHAR(255)");
            }
            sb.append(")");

            // execute the SQL query to create the table
            jdbcTemplate.execute(sb.toString());
        } catch (IOException e) {
            // handle the IOException here, for example by logging the error and displaying a user-friendly message
            logger.error("Error parsing HTML file: {}", e.getMessage());
            throw new RuntimeException("Error creating table: please try again later.");
        }
    }


    @Override
    public void saveInputData(HtmlFile htmlFile, String inputName, String inputValue) {
        String tableName = htmlFile.getName().replace(".html", "");

        // create a SQL query to insert the input data into the correct column
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append("(").append(inputName).append(") VALUES ('").append(inputValue).append("')");

        // execute the SQL query to insert the input data
        jdbcTemplate.execute(sb.toString());
    }


    @Override
    public BufferedImage takeScreenshot(HtmlFile htmlFile) throws AWTException, IOException {
        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenshot = robot.createScreenCapture(screenRect);

        String screenshotFilename = "screenshot_" + htmlFile.getName().replace(".html", ".png");
        File screenshotFile = new File(screenshotFilename);
        ImageIO.write(screenshot, "png", screenshotFile);

        return screenshot;
    }
}