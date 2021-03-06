package com.bigdatacompany.eticaret;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.producer.ProducerConfig;
import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
public class MessageProducer {

    Producer producer;

    @PostConstruct
    public void init(){
        Properties config = new Properties();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"188.166.158.90:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,new StringSerializer().getClass().getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,new StringSerializer().getClass().getName());

        producer = new KafkaProducer<String,String>(config);


    }
    public void send(String term){
        ProducerRecord<String,String> rec =new ProducerRecord<String,String>("search-analysis-stream",term);
        producer.send(rec);
    }

    public void close(){
        producer.close();
    }
}
