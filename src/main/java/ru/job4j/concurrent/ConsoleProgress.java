package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(6000);
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] sphere = {'\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
               for (char c : sphere) {
                   System.out.print("\r Loading ..." + c);
                   try {
                       Thread.sleep(150);
                   } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                   }
               }
        }
    }
}

