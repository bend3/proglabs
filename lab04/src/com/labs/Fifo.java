package com.labs;

import java.util.LinkedList;

public class Fifo{
    LinkedList<String> list = new LinkedList<String>();

    synchronized public void put(String string) throws InterruptedException {
        if (list.size() >= 10){
            this.wait();
        }
        loop: while (true){
            if (list.size() < 10) {
                break loop;
            } else {
                this.wait();
            }
        }
        notifyAll();
        list.add(string);
    }

    synchronized public String get() throws InterruptedException {
        if (list.isEmpty()){
            this.wait();
        }
        loop: while (true){
            if (!list.isEmpty()) {
                break loop;
            } else {
                this.wait();
            }
        }
        notifyAll();
        System.out.println(Thread.currentThread().getName());
        return list.remove();
    }
}
