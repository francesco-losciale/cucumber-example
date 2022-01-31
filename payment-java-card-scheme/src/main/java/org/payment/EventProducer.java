package org.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventProducer {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void produce(String key, Event event) throws JsonProcessingException {
        var record = new ProducerRecord<>("card-scheme-transaction-topic",
                key,
                objectMapper.writeValueAsString(event));
        record.headers().add("eventType", event.eventType().name().getBytes());
        kafkaTemplate.send(record);
    }
}
