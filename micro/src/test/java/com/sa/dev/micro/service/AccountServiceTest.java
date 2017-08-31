package com.sa.dev.micro.service;

import com.google.common.collect.Lists;
import com.sa.dev.micro.TestApp;
import com.sa.dev.micro.entity.Account;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sujitagarwal on 27/08/17.
 */
@SpringBootTest
public class AccountServiceTest extends TestApp {

    @Autowired
    AccountService accountService;

    @Test
    public void create() {
        Account account = accountService.createAccount(getAccount());
        assertThat(account).isNotNull();
        assertThat(account.getAccountNumber()).isEqualTo(getAccount().getAccountNumber());
    }

    @Test
    public void findAll() {
        List<Account> accounts = Lists.newArrayList(accountService.findAll());
        assertThat(accounts).hasSize(5);

    }

    @Test
    public void findByAccountNumber() {
        Account account = accountService.findByAccountNumber(getAccount().getAccountNumber());
        assertThat(account).isNotNull();
        assertThat(account.getAccountNumber()).isEqualTo(getAccount().getAccountNumber());
    }


}
