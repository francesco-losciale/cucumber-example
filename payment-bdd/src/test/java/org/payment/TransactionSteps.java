package org.payment;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.payment.client.Account;
import org.payment.client.AccountStatus;
import org.payment.client.IssuerBankRepository;
import org.payment.producer.KafkaUtil;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.payment.client.FundStatus.AVAILABLE;

public class TransactionSteps {

    final IssuerBankRepository target = Feign.builder()
            .encoder(new JacksonEncoder())
            .decoder(new JacksonDecoder())
            .target(IssuerBankRepository.class, "http://localhost:8080/api/v1");

    final String ACCOUNT_ID = UUID.randomUUID().toString();

    final Account ACCOUNT_OBJECT =
            Account.builder()
                    .id(ACCOUNT_ID)
                    .amount(123L)
                    .accountStatus(
                            AccountStatus.builder()
                                    .fundStatus(AVAILABLE)
                                    .build())
                    .build();

    @Given("the customer has a bank account")
    public void the_customer_has_a_bank_account() {
        target.add(ACCOUNT_OBJECT);
    }

    @When("the transaction is requested by the merchant")
    public void the_transaction_is_requested_by_the_merchant() {
        var kafkaUtil = new KafkaUtil();
        kafkaUtil.produceTransactionRequestEvent();
    }

    @Then("the amount will be debited")
    public void the_amount_will_be_debited() {
        assertThat(target.get(ACCOUNT_ID)).isEqualTo(ACCOUNT_OBJECT);
    }

}
