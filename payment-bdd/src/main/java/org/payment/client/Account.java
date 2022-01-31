package org.payment.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Account {

    private String id;
    private Long amount;
    private AccountStatus accountStatus;

    @JsonCreator
    private Account(@JsonProperty("id") String id,
                    @JsonProperty("amount") Long amount,
                    @JsonProperty("accountStatus") AccountStatus accountStatus) {
        this.id = id;
        this.amount = amount;
        this.accountStatus = accountStatus;
    }
}
