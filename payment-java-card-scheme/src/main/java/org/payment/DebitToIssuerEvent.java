package org.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static org.payment.EventType.DEBIT_TO_ISSUER_EVENT;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public class DebitToIssuerEvent implements Event {

    private final Long id;
    private final String issuerId;
    private final BigDecimal amount;

    public static DebitToIssuerEvent from(TransferRequestedEvent transferRequestedEvent) {
        return new DebitToIssuerEvent(transferRequestedEvent.getId(),
                transferRequestedEvent.getIssuerId(),
                transferRequestedEvent.getAmount());
    }

    @Override
    public EventType eventType() {
        return DEBIT_TO_ISSUER_EVENT;
    }

    @Override
    public String key() {
        return id.toString();
    }
}
