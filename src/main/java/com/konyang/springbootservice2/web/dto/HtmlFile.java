package com.konyang.springbootservice2.web.dto;

public class HtmlFile {
    private String name; // file name
    private int inputCount; // number of input tags in the HTML file

    public HtmlFile(String name, int inputCount) {
        this.name = name;
        this.inputCount = inputCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInputCount() {
        return inputCount;
    }

    public void setInputCount(int inputCount) {
        this.inputCount = inputCount;
    }
}
