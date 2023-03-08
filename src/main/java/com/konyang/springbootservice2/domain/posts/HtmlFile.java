package com.konyang.springbootservice2.domain.posts;

public class HtmlFile {
    private String name;
    private int inputCount;

    public HtmlFile(String name, int inputCount) {
        this.name = name;
        this.inputCount = inputCount;
    }

    public String getName() {
        return name;
    }

    public int getInputCount() {
        return inputCount;
    }
}
