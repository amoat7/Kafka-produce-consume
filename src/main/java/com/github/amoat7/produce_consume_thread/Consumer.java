package com.github.amoat7.produce_consume_thread;

import com.github.amoat7.produce.consume.ProducerDemoKeys;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import static com.github.amoat7.produce_consume_thread.ThreadColour.ANSI_GREEN;

public class Consumer {
    private Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);
    // create the consumer
    private KafkaConsumer<String, String> consumer;
    private ConsumerRecords<String, String> records;

    // create Producer properties
    private Properties properties = new Properties();

    public Consumer() {
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-seventh1-application");
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); //earliest/latest/none

        consumer = new KafkaConsumer<String, String>(properties);



        // subscribe to the consumer to topics
        consumer.subscribe(Arrays.asList("first_topic"));

    }

    // poll for new data
    public void poll_data(){
        while (true){
            records = consumer.poll(Duration.ofMillis(100)); // commit every 100 ms
            for (ConsumerRecord<String, String> record : records){
                logger.info(ANSI_GREEN + "Key " + record.key() + ", Value: " + record.value());
                logger.info(ANSI_GREEN +"Partition: "+ record.partition() + "Offset: " + record.offset());
            }

        }

    }
    // wake up
    public void wake_up(){
        consumer.wakeup();
    }

    // close
    public void close_(){
        consumer.close();
    }


}
