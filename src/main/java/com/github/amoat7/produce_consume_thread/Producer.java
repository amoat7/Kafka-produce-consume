package com.github.amoat7.produce_consume_thread;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static com.github.amoat7.produce_consume_thread.ThreadColour.ANSI_RED;

public class Producer {

    Logger logger = LoggerFactory.getLogger(Producer.class);

    // create Producer properties
    private Properties properties = new Properties();
    private KafkaProducer<String, String> producer;
    private ProducerRecord<String, String> record;

    public Producer() {
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        // create the producer
         producer = new KafkaProducer<String, String>(properties);

    }
    // implements send method
    public void send_data(){

        for(int i =0; i<1000000000; i++){
            // create a producer record
            record = new ProducerRecord<String, String>("first_topic", "hello_world" + i);
            producer.send(record, new call_back());
        }

        producer.flush();
        producer.close();
    }

    // implements a Callback
    class call_back implements  Callback{
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e == null){
                // record was successfully sent
                logger.info(ANSI_RED+ "Record sent succesfully");
                /*logger.info(ANSI_RED + "Received new metadata \n" +
                        "Topic : " + recordMetadata.topic()+ "\n"+
                        "Partition: "+ recordMetadata.partition() + "\n" +
                        "Offset: " + recordMetadata.offset() + "\n" +
                        "Timestamp" + recordMetadata.timestamp());*/
            }
            else{
                logger.error("Error while producing", e);

            }
        }
    }
}



