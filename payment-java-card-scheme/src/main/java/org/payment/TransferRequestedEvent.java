package org.payment;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class TransferRequestedEvent {
    public static final String TRANSACTION_REQUESTED_EVENT = "TRANSACTION_REQUESTED_EVENT";

    private final Long id;
    private final String issuerId;
    private final String acquirerId;
    private final BigDecimal amount;

    @JsonCreator
    public TransferRequestedEvent(@JsonProperty("id") Long id,
                                  @JsonProperty("issuerId") String issuerId,
                                  @JsonProperty("acquirerId") String acquirerId,
                                  @JsonProperty("amount") BigDecimal amount) {
        this.id = id;
        this.issuerId = issuerId;
        this.acquirerId = acquirerId;
        this.amount = amount;
    }
}
