package com.examples;

public class UseLimitInstanceClass {

    private static final int SIZE = 10;

    public static void main(String[] args) {
        AccessLimitInstanceClassThread t = new AccessLimitInstanceClassThread();
        for (int i = 0; i < SIZE; i++) {
            new Thread(t, "Thread: " + i).start();
        }
    }
}
