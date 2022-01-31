package org.payment.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountStatus {

    private final FundStatus fundStatus;

    @JsonCreator
    private AccountStatus(@JsonProperty("fundStatus") FundStatus fundStatus) {
        this.fundStatus = fundStatus;
    }

}
