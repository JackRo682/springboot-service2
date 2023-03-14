package com.konyang.springbootservice2.web;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RenderHtmlController {
    private final JdbcTemplate jdbcTemplate;

    public RenderHtmlController(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @GetMapping("/render-html")
    public String renderHtml(Model model) {
        // Load the HTML template into a string
        String html = loadHtmlTemplate();

        // Inject the JavaScript code into the HTML template
        String injectedHtml = injectJavaScriptCode(html);

        // Add the injected HTML to the model
        model.addAttribute("injectedHtml", injectedHtml);

        // Render the HTML template with the injected JavaScript code
        return "rendered-html-template";
    }

    @PostMapping("/submit-form")
    public String submitForm(@RequestParam("Name") String name, @RequestParam("Phone Number") String phoneNumber, @RequestParam("Email") String email, @RequestParam("Message") String message) {
        // Insert the form data into the database using JdbcTemplate
        String sql = "INSERT INTO form_data (name, phone_number, email, message) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, phoneNumber, email, message);

        return "redirect:/success-page";
    }

    private String loadHtmlTemplate() {
        // Load the HTML template from a file or database
        // and return it as a string
    }

    private String injectJavaScriptCode(String html) {
        // Inject the JavaScript code into the HTML template
        // and return the modified HTML as a string
    }
}
