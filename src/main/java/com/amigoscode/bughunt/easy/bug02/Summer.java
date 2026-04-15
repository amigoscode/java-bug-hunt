package com.amigoscode.bughunt.easy.bug02;

public class Summer {

    public int sum(int[] numbers) {
        int total = 0;
        for (int i = 0; i <= numbers.length; i++) {
            total += numbers[i];
        }
        return total;
    }
}
