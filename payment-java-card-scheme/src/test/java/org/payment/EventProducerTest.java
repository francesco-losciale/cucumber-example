package org.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.payment.EventType.UNKNOWN;

@SpringBootTest
@DirtiesContext
class EventProducerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventProducer eventProducer;

    @MockBean
    KafkaTemplate<String, String> kafkaTemplate;

    @Captor
    ArgumentCaptor<ProducerRecord> argumentCaptor;

    @Test
    void shouldProduceEvent() throws JsonProcessingException {
        Event event = createMockEvent();
        var expected = transformInExpectedKafkaRecord(event);

        eventProducer.produce(event.key(), event);

        verify(kafkaTemplate).send(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue()).isEqualTo(expected);
    }

    private ProducerRecord<String, String> transformInExpectedKafkaRecord(Event event) throws JsonProcessingException {
        ProducerRecord<String, String> record =
                new ProducerRecord<>("card-scheme-transaction-topic",
                        "key",
                        objectMapper.writeValueAsString(event));
        record.headers().add("eventType", UNKNOWN.name().getBytes());
        return record;
    }

    private Event createMockEvent() {
        return new Event() {
            private final String field = "test";

            public String getField() {
                return field;
            }

            @Override
            public EventType eventType() {
                return UNKNOWN;
            }

            @Override
            public String key() {
                return "key";
            }
        };
    }
}