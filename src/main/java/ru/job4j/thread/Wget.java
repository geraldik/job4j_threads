package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final ArgsName jvm;

    public Wget(String[] args) {
        this.jvm = ArgsName.of(args);
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(jvm.get("l")).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(jvm.get("o"))) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int downloadData = 0;
            long startTimeMillis = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                downloadData += bytesRead;
                if (downloadData == Integer.parseInt(jvm.get("s")) * 1048576) {
                    long endTimeMillis = System.currentTimeMillis();
                    if ((endTimeMillis - startTimeMillis) < 1000) {
                        Thread.sleep(endTimeMillis - startTimeMillis);
                    }
                }
                downloadData = 0;
                startTimeMillis = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void inputValidating() {
        if (jvm.size() != 3) {
            throw new IllegalArgumentException("Wrong amount of arguments. Use java -jar Wget.jar"
                    + " -l=DOWNLOADING_LINK -o=OUTPUT_PATH -s=DOWNLOADING_SPEED");
        }
        if (jvm.get("l") == null
                || jvm.get("o") == null
                || jvm.get("s") == null) {
            throw new IllegalArgumentException("Wrong set of keys. Use java -jar Wget.jar"
                    + " -l=DOWNLOADING_LINK -o=OUTPUT_PATH -s=DOWNLOADING_SPEED");
        }
        try {
            Integer.parseInt(jvm.get("s"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Wrong format of speed argument."
                    + " Speed argument must be is numeric.");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread wget = new Thread(new Wget(args));
        wget.start();
        wget.join();
    }
}