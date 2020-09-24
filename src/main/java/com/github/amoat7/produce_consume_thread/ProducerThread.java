package com.github.amoat7.produce_consume_thread;

public class ProducerThread implements Runnable{
    private Producer producer = new Producer();

    public ProducerThread() {

    }

    @Override
    public void run() {
        producer.send_data();

    }
}
