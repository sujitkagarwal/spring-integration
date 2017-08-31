package com.sa.dev.micro.service.impl;

import com.google.common.collect.Lists;
import com.sa.dev.micro.entity.Account;
import com.sa.dev.micro.repository.AccountRepository;
import com.sa.dev.micro.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sujitagarwal on 25/08/17.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    protected AccountRepository accountRepository;

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Account> findAll() {
        return Lists.newArrayList(accountRepository.findAll());
    }

    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
}
