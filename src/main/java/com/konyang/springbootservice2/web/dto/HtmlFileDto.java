package com.konyang.springbootservice2.web.dto;

public class HtmlFileDto {
    private String name;
    private int inputCount;

    public HtmlFileDto(String name, int inputCount) {
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
