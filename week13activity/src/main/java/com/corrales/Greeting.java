package com.corrales;

public class Greeting implements Runnable {

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                // System.out.println("[System Ready] You can input grades anytime...");
                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        } finally {
            System.out.println("Greeting stopped.");
        }
    }
}
