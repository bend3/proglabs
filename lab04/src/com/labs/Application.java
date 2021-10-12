package com.labs;

public class Application {

    public static void main(String[] args) {
        Fifo fifo = new Fifo();
	    Thread consumer1 = new Consumer(fifo, "consumer1", 300);
	    consumer1.setName("consumer1");
	    Thread consumer2 = new Consumer(fifo, "consumer2", 300);
	    consumer1.setName("consumer2");
	    Thread consumer3 = new Consumer(fifo, "consumer3", 300);
	    consumer1.setName("consumer3");
	    Thread consumer4 = new Consumer(fifo, "consumer4", 300);
	    consumer1.setName("consumer4");
	    Thread producerThread1 = new Thread(new Producer("demo1", fifo, 400));
	    Thread producerThread2 = new Thread(new Producer("demo2", fifo, 400));
	    Thread producerThread3 = new Thread(new Producer("demo3", fifo, 400));

	    consumer1.start();
	    consumer2.start();
	    consumer3.start();
	    consumer4.start();
	    producerThread1.start();
	    producerThread2.start();
	    producerThread3.start();
    }
}
