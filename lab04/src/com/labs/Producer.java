package com.labs;

public class Producer implements Runnable{
    private Fifo fifo;
    private String string;
    private int counter;
    int n;
    public Producer(String string, Fifo fifo, int n) {
        this.n = n;
        this.string = string;
        this.fifo = fifo;
        counter = 0;
    }

    public void go(){
        while (true){

            long time  = System.currentTimeMillis() % 100000;
            String res = string + " " + counter;
            try {
                fifo.put(res);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("produced " + string + " " + counter++ + " " + time);
            try {
                Thread.sleep(Double.valueOf(Math.random()*n).longValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        go();
    }
}
