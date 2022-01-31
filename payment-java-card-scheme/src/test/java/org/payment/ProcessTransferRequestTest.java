package org.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext
public class ProcessTransferRequestTest {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @MockBean
    EventProducer eventProducer;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shouldDebitIssuerAndCreditAcquirer() throws JsonProcessingException {
        TransferRequestedEvent incomingTransferEvent = createTransferRequestEvent();
        String messageKey = incomingTransferEvent.getId().toString();
        DebitToIssuerEvent expectedDebitToIssuerEvent = DebitToIssuerEvent.from(incomingTransferEvent);
        CreditToAcquirerEvent expectedCreditToAcquirerEvent = CreditToAcquirerEvent.from(incomingTransferEvent);
        ProducerRecord<String, String> input = transformToKafkaMessage(incomingTransferEvent);

        kafkaTemplate.send(input);

        await().atMost(Duration.ofSeconds(5))
                .untilAsserted(() -> {
                    verify(eventProducer).produce(messageKey, expectedDebitToIssuerEvent);
                    verify(eventProducer).produce(messageKey, expectedCreditToAcquirerEvent);
                });
    }

    private TransferRequestedEvent createTransferRequestEvent() {
        return new TransferRequestedEvent(1L,
                "acquirer id",
                "issuer id",
                BigDecimal.valueOf(12345.99));
    }

    private ProducerRecord<String, String> transformToKafkaMessage(TransferRequestedEvent transferRequestEvent) throws JsonProcessingException {
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader("eventType", TransferRequestedEvent.TRANSACTION_REQUESTED_EVENT.getBytes()));
        return new ProducerRecord<>("card-scheme-transaction-topic",
                0,
                transferRequestEvent.getId().toString(),
                objectMapper.writeValueAsString(transferRequestEvent),
                headers);
    }
}
