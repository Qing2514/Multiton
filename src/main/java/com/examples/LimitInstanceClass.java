package com.examples;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LimitInstanceClass {

    private final int id;
    private boolean isBusy;
    private String accessMessage;
    private static final List<LimitInstanceClass> INSTANCE_LIST = new ArrayList<>();
    // cfg 文件的内容读出后会转变为 ASCII 值，其中的 0 读出后为 48
    private static final int SIZE = getNumberOfInstances() - 48;

    private static final String FILE = "InstanceLimit.cfg";

    private LimitInstanceClass(int id) {
        this.id = id;
    }

    static {
        for (int i = 0; i < SIZE; i++) {
            INSTANCE_LIST.add(new LimitInstanceClass(i));
        }
    }

    public static LimitInstanceClass getInstance() {
        for (int i = 0; ; i++) {
            if (i == SIZE) {
                i %= SIZE;
            }
            // 在创建对象之前，添加一次检查，避免不必要的锁定，提高效率
            if (!INSTANCE_LIST.get(i).isBusy) {
                synchronized (LimitInstanceClass.class) {
                    if (!INSTANCE_LIST.get(i).isBusy) {
                        INSTANCE_LIST.get(i).isBusy = true;
                        return INSTANCE_LIST.get(i);
                    }
                }
            }
        }
    }

    // public static LimitInstanceClass getInstance() {
    //     for (int i = 0; i < SIZE; i++) {
    //         // 在创建对象之前，添加一次检查，避免不必要的锁定，提高效率
    //         if (!INSTANCE_LIST.get(i).isBusy) {
    //             synchronized (LimitInstanceClass.class) {
    //                 if (!INSTANCE_LIST.get(i).isBusy) {
    //                     INSTANCE_LIST.get(i).isBusy = true;
    //                     return INSTANCE_LIST.get(i);
    //                 }
    //             }
    //         }
    //     }
    //     return null;
    // }

    // 获取指定文件中的数
    private static int getNumberOfInstances() {
        int value = 0;
        try {
            BufferedInputStream input = new BufferedInputStream(new FileInputStream(FILE));
            value = input.read();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void release() {
        this.isBusy = false;
    }

    public void writeAccessMessage(String message) {
        this.accessMessage = message;
    }

    public void printAccessMessage() {
        System.out.println(accessMessage + "\tid: " + id);
    }
}
