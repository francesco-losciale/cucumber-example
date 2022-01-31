package org.payment.paymentjavabankissuer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class AccountStatus {

    @JsonProperty
    private FundStatus fundStatus;

    @JsonCreator
    private AccountStatus(@JsonProperty("fundStatus") FundStatus fundStatus) {
        this.fundStatus = fundStatus;
    }
}
