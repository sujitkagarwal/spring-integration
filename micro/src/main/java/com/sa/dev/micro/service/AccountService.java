package com.sa.dev.micro.service;

import com.sa.dev.micro.entity.Account;

import java.util.List;

/**
 * Created by sujitagarwal on 25/08/17.
 */
public interface AccountService {
    Account findByAccountNumber(String accountNumber);

    List<Account> findAll();

    Account createAccount(Account account);

}
