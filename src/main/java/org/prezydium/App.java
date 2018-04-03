package org.prezydium;

public class App {

    public static void main(String[] args) {
        AppWindow appWindow = new AppWindow();
        Consumer consumer = new Consumer(appWindow);
        consumer.run();
    }
}
