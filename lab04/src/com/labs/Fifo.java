package com.labs;

import java.util.LinkedList;

public class Fifo{
    LinkedList<String> list = new LinkedList<String>();

    synchronized public void put(String string) throws InterruptedException {
        if (list.size() >= 10){
            this.wait();
        }
        notify();
        list.add(string);
    }

    synchronized public String get() throws InterruptedException {
        if (list.isEmpty()){
            this.wait();
        }
        notify();
        return list.remove();
    }
}
