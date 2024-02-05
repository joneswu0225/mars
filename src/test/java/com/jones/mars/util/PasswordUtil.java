package com.jones.mars.util;

import java.util.regex.Pattern;

public class PasswordUtil {
    public static void main(String[] args) {
        String pattern = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,32}";
        String password = "a2$d";
        System.out.println(Pattern.matches(pattern, "2222a2$d"));
        System.out.println(Pattern.matches(pattern, "@aaAAASS"));
    }
}
