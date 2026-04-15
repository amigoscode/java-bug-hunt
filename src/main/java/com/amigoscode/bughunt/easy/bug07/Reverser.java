package com.amigoscode.bughunt.easy.bug07;

public class Reverser {

    public String reverse(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = input.length(); i >= 0; i--) {
            sb.append(input.charAt(i));
        }
        return sb.toString();
    }
}
