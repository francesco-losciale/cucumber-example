package org.payment.paymentjavabankissuer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.payment.paymentjavabankissuer.AccountTestUtils.ACCOUNT_JSON_PAYLOAD;
import static org.payment.paymentjavabankissuer.AccountTestUtils.ACCOUNT_OBJECT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreate() {
        var url = "http://localhost:" + port + "/api/v1/accounts";
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(ACCOUNT_JSON_PAYLOAD, headers);

        ResponseEntity<Account> forEntity = restTemplate.postForEntity(url, request, Account.class);

        assertThat(forEntity).isNotNull();
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(forEntity.getBody()).isEqualTo(ACCOUNT_OBJECT);
    }

    @Test
    void shouldGet() {
        var url = "http://localhost:" + port + "/api/v1/accounts";
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var request = new HttpEntity<>(ACCOUNT_JSON_PAYLOAD, headers);
        restTemplate.postForObject(url, request, String.class);

        ResponseEntity<Account> forEntity = restTemplate.getForEntity(url + "/test", Account.class);

        assertThat(forEntity).isNotNull();
        assertThat(forEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(forEntity.getBody()).isEqualTo(ACCOUNT_OBJECT);
    }
}
