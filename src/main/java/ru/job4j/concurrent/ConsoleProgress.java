package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(7000);
        progress.interrupt();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
               char[] sphere = {'\\', '|', '/'};
               for (char c : sphere) {
                   System.out.print("\r Loading ..." + c);
                   try {
                       Thread.sleep(100);
                   } catch (InterruptedException e) {
                       Thread.currentThread().interrupt();
                   }
               }
        }
    }
}

