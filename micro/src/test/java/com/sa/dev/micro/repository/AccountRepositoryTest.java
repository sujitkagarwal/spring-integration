package com.sa.dev.micro.repository;

import com.google.common.collect.Lists;
import com.sa.dev.micro.MicroApplication;
import com.sa.dev.micro.TestApp;
import com.sa.dev.micro.entity.Account;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sujitagarwal on 25/08/17.
 */
@SpringBootTest(classes = MicroApplication.class)
public class AccountRepositoryTest extends TestApp {
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void create() {
        Account account = accountRepository.save(getAccount());
        assertThat(account).isNotNull();
        assertThat(account.getAccountNumber()).isEqualTo(getAccount().getAccountNumber());
    }

    @Test
    public void findAll() {
        List<Account> accounts = Lists.newArrayList(accountRepository.findAll());
        assertThat(accounts).hasSize(5);

    }

    @Test
    public void findByAccountNumber() {
        Account account = accountRepository.findByAccountNumber(getAccount().getAccountNumber());
        assertThat(account).isNotNull();
        assertThat(account.getAccountNumber()).isEqualTo(getAccount().getAccountNumber());
    }

    @Test
    public void countAccounts() {
        assertThat(accountRepository.countAccounts()).isEqualTo(5);
    }


}
