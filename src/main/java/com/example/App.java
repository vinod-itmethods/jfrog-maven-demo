package com.example;

import org.apache.commons.lang3.StringUtils;

public class App {
    public static void main(String[] args) {
        String input = " Hello JFrog ";
        System.out.println("Original: '" + input + "'");
        System.out.println("Trimmed: '" + StringUtils.trim(input) + "'");
    }
}
