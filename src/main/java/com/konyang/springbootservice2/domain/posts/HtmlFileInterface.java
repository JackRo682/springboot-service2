package com.konyang.springbootservice2.domain.posts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface HtmlFileInterface {
    void createTable(HtmlFileDto htmlFile);

    void saveInputData(HtmlFileDto htmlFile, String inputName, String inputValue);

    BufferedImage takeScreenshot(HtmlFileDto htmlFile) throws AWTException, IOException;
}
