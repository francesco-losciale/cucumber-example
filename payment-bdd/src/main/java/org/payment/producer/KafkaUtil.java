package org.payment.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaUtil {

    public void produceTransactionRequestEvent() {
        String topicName = "card-scheme-transaction-topic";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9093");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> kafkaProducer = new KafkaProducer<>(props);
        String transactionId = "123";
        String payload = "{\"id\": 345,\"issuerId\": \"issuer example\",\"acquirerId\": \"acquirer example\",\"amount\": 20001.99}";
        kafkaProducer.send(new ProducerRecord<>(topicName, transactionId, payload));
    }

}
