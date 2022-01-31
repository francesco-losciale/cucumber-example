package org.payment;

import java.math.BigDecimal;

public class TestUtils {

    public static TransferRequestedEvent createTransferRequestEvent() {
        return new TransferRequestedEvent(1L,
                "issuer id",
                "acquirer id",
                BigDecimal.valueOf(12345.99));
    }
}
