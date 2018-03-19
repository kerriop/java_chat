package com.dima;

public class Main {
	/**
	 * Порт, на котором будет работать сервер и клиенты
	 */
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
