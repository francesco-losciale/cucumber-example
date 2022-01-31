package org.payment.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface IssuerBankRepository {

    @RequestLine("POST /accounts")
    @Headers("Content-Type: application/json")
    void add(Account account);

    @RequestLine("GET /accounts/{id}")
    Account get(@Param("id") String id);

}
