package com.konyang.springbootservice2.domain.posts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface HtmlFileService {
    void createTable(HtmlFile htmlFile);

    void saveInputData(HtmlFile htmlFile, String inputName, String inputValue);

    BufferedImage takeScreenshot(HtmlFile htmlFile) throws AWTException, IOException;
}
