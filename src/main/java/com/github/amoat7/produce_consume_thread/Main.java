package com.github.amoat7.produce_consume_thread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.amoat7.produce_consume_thread.ThreadColour.ANSI_PURPLE;

public class Main {


    public static void main(String[] args) {

        // start the thread
        Logger logger = LoggerFactory.getLogger(Consumer.class);

        ConsumerThread consumer_thread = new ConsumerThread();
        Thread t1= new Thread(consumer_thread);
        t1.start();

        ProducerThread producer_thread = new ProducerThread();
        Thread t2 = new Thread(producer_thread);
        t2.start();

        // add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            logger.info(ANSI_PURPLE + "Caught shutdown hook");
            consumer_thread.shutdown();
        }));


    }

}


