package com.labs;

public class Consumer extends Thread{
    Fifo fifo;
    String string;
    int n;

    public Consumer(Fifo fifo, String string, int n) {
        this.fifo = fifo;
        this.string = string;
        this.n = n;
        setName("consumer");
    }

    @Override
    public void run() {
        while (true){
            try {
                long time  = System.currentTimeMillis() % 100000;
                String consumedString = fifo.get();
                System.out.println("consumed " + string + " " + consumedString + " " + time);
                sleep(Double.valueOf(Math.random()*n).longValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
