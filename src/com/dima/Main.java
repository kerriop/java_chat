package com.dima;

public class Main {
    public static final int PORT = 5482;

    public static void main(String[] args) {
        MainWindow instance = new MainWindow();
        instance.pack();
        instance.setSize(400, 300);
        instance.setLocationByPlatform(true);
        instance.setVisible(true);
        System.exit(0);
    }
}
