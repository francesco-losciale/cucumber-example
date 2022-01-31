package org.payment.paymentjavabankissuer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.payment.paymentjavabankissuer.FundStatus.AVAILABLE;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final Map<String, Account> map = new HashMap<>();

    @PostMapping
    ResponseEntity<Account> create(@RequestBody Account account) {
        map.put(account.getId(), account);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/{id}")
    ResponseEntity<Account> status(@PathVariable String id) {
        Account account = map.get(id);
        account.setAccountStatus(AccountStatus.builder().fundStatus(AVAILABLE).build());        // TODO
        System.out.println(account);
        return ResponseEntity.ok(account);
    }
}
