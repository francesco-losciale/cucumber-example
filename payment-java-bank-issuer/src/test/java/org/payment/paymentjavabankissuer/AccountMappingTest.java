package org.payment.paymentjavabankissuer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.payment.paymentjavabankissuer.AccountTestUtils.ACCOUNT_JSON_PAYLOAD;
import static org.payment.paymentjavabankissuer.AccountTestUtils.ACCOUNT_OBJECT;
import static org.payment.paymentjavabankissuer.FundStatus.AVAILABLE;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;

public class AccountMappingTest {

    ObjectMapper om = new ObjectMapperConfiguration().objectMapper();

    @Test
    void shouldDeserialize() throws JsonProcessingException {
        Account account = om.readValue(ACCOUNT_JSON_PAYLOAD, Account.class);

        assertThat(account.getAccountStatus()).isEqualTo(AccountStatus.builder().fundStatus(AVAILABLE).build());
    }

    @Test
    void shouldSerialize() throws JsonProcessingException, JSONException {
        String json = om.writeValueAsString(ACCOUNT_OBJECT);

        assertEquals(json, ACCOUNT_JSON_PAYLOAD, LENIENT);
    }
}
