package org.payment;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static org.payment.EventType.CREDIT_TO_ACQUIRER_EVENT;

@EqualsAndHashCode
@RequiredArgsConstructor
public class CreditToAcquirerEvent implements Event {

    private final Long id;
    private final String acquirerId;
    private final BigDecimal amount;

    public static CreditToAcquirerEvent from(TransferRequestedEvent transferRequestedEvent) {
        return new CreditToAcquirerEvent(transferRequestedEvent.getId(),
                transferRequestedEvent.getAcquirerId(),
                transferRequestedEvent.getAmount());
    }

    @Override
    public EventType eventType() {
        return CREDIT_TO_ACQUIRER_EVENT;
    }

    @Override
    public String key() {
        return id.toString();
    }
}
