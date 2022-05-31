package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(2000);
        progress.interrupt();
    }

    @Override
    public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.print("\r Loading ...\\");
                    Thread.sleep(100);
                    System.out.print("\r Loading ...|");
                    Thread.sleep(100);
                    System.out.print("\r Loading .../");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
            }
        }
    }

