package com.examples;

import java.util.Random;

public class AccessLimitInstanceClassThread implements Runnable {
    @Override
    public void run() {
        LimitInstanceClass instance = LimitInstanceClass.getInstance();
        try {
            instance.writeAccessMessage(Thread.currentThread().getName());
            // 随机数范围为 [0, 6)
            int i = new Random().nextInt(5 + 1);
            Thread.sleep(i * 1000);
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " is null");
            return;
        }
        instance.printAccessMessage();
        instance.release();
    }
}
