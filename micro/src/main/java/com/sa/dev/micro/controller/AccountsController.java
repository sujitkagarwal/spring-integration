package com.sa.dev.micro.controller;


import com.sa.dev.micro.entity.Account;
import com.sa.dev.micro.exceptions.AccountNotFoundException;
import com.sa.dev.micro.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * A RESTFul controller for accessing account information.
 *
 * @author Paul Chapman
 */
@RestController
public class AccountsController {

    protected Logger logger = Logger.getLogger(AccountsController.class
            .getName());

    @Autowired
    protected AccountService accountService;


    /**
     * Fetch an account with the specified account number.n
     *
     * @param accountNumber A numeric, 9 digit account number.
     * @return The account if found.
     * @throws com.sa.dev.micro.exceptions.AccountNotFoundException If the number is not recognised.
     */
    @GetMapping("/accounts/account/{accountNumber}")
    public Account byAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        logger.info("accounts-service byNumber() invoked: " + accountNumber);
        Account account = accountService.findByAccountNumber(accountNumber);
        logger.info("accounts-service byNumber() found: " + account);

        if (account == null)
            throw new AccountNotFoundException(accountNumber);
        else {
            return account;
        }
    }

    /**
     * Fetch accounts with the specified name. A partial case-insensitive match
     * is supported. So <code>http://.../accounts</code> will find any
     * accounts with upper or lower case 'a' in their name.
     * <p>
     * //@param partialName
     *
     * @return A non-null, non-empty set of accounts.
     * @throws AccountNotFoundException If there are no matches at all.
     */
    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        logger.info("accounts-service is invoked grtAccounts(): "
                + accountService.getClass().getName() + " for "
        );
        List<Account> accounts = accountService.findAll();
        logger.info("accounts-service byOwner() found: " + accounts);
        if (accounts == null || accounts.size() == 0)
            throw new AccountNotFoundException("");
        else {
            return accounts;
        }
    }


    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
}