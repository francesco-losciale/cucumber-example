package org.payment.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FundStatus {
    AVAILABLE,
    NOT_AVAILABLE;
}
