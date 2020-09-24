package com.github.amoat7.produce_consume_thread;

import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ConsumerThread implements Runnable {

    private CountDownLatch latch = new CountDownLatch(1);
    private Consumer consumer = new Consumer();
    private Logger logger = LoggerFactory.getLogger(Consumer.class);

    public ConsumerThread() {

    }

    @Override
    public void run() {
        try {
            consumer.poll_data();
        } catch (WakeupException e) {
            logger.info("Received shutdown signal");
        } finally {
            consumer.close_();
            // tell our main code we are done with the consumer
            latch.countDown();
        }



    }

    public void shutdown(){
        // interrupts consumer.poll()
        // it will throw a wakeup exception
        consumer.wake_up();

    }
}
