package com.amigoscode.bughunt.easy.bug36;

public class RequestCounter {

    private static int count = 0;
    private final String endpoint;

    public RequestCounter(String endpoint) {
        this.endpoint = endpoint;
    }

    public int record() {
        count++;
        return count;
    }

    public int recordBatch(int n) {
        for (int i = 0; i < n; i++) {
            record();
        }
        return count;
    }

    public int current() {
        return count;
    }

    public void reset() {
        count = 0;
    }

    public String endpoint() {
        return endpoint;
    }

    public String summary() {
        return endpoint + ": " + count + " requests";
    }
}
