package org.payment.paymentjavabankissuer;

import static org.payment.paymentjavabankissuer.FundStatus.AVAILABLE;

public class AccountTestUtils {


    //language=JSON
    public static final String ACCOUNT_JSON_PAYLOAD =
            "{\n" +
                    "  \"id\": \"test\",\n" +
                    "  \"amount\": 123,\n" +
                    "  \"accountStatus\": {\n" +
                    "    \"fundStatus\": \"AVAILABLE\"\n" +
                    "  }\n" +
                    "}";

    public static final Account ACCOUNT_OBJECT =
            Account.builder()
                    .id("test")
                    .amount(123L)
                    .accountStatus(
                            AccountStatus.builder()
                                    .fundStatus(AVAILABLE)
                                    .build())
                    .build();
}
