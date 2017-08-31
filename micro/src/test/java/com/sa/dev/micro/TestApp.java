package com.sa.dev.micro;

import com.sa.dev.micro.entity.Account;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by sujitagarwal on 29/08/17.
 */


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@PropertySource("classpath:application-test.properties")
public abstract class TestApp {

    protected Account getAccount() {
        Account account = new Account();
        account.setFirstName("sujit");
        account.setLastName("agarwal");
        account.setAccountNumber("1234");
        account.setStatus("ACT");
        return account;
    }
}
