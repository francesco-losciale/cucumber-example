package org.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import static org.payment.TransferRequestedEvent.TRANSACTION_REQUESTED_EVENT;

@RequiredArgsConstructor
@SpringBootApplication
public class CardSchemeApplication {

    private final EventProducer eventProducer;

    public static void main(String[] args) {
        SpringApplication.run(CardSchemeApplication.class);
    }

    @KafkaListener(id = "transaction-listener", topics = "card-scheme-transaction-topic")
    public void newLedgerEntry(@Payload TransferRequestedEvent transferRequestedEvent,
                               @Header("eventType") String messageType) throws JsonProcessingException {
        if (!messageType.equals(TRANSACTION_REQUESTED_EVENT)) return;
        IssuerClient issuerClient = new IssuerClient(); // TODO implement
        boolean hasFunds = issuerClient.hasFunds(transferRequestedEvent.getIssuerId(), transferRequestedEvent.getAmount());
        if (hasFunds) {
            DebitToIssuerEvent debitToIssuerEvent = DebitToIssuerEvent.from(transferRequestedEvent);
            CreditToAcquirerEvent creditToAcquirerEvent = CreditToAcquirerEvent.from(transferRequestedEvent);
            eventProducer.produce(transferRequestedEvent.getId().toString(), debitToIssuerEvent);
            eventProducer.produce(transferRequestedEvent.getId().toString(), creditToAcquirerEvent);
        }

        System.out.println("Received message:");
        System.out.println(transferRequestedEvent);
    }

}
