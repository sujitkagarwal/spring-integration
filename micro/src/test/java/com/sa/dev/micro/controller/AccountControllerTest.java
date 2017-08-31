package com.sa.dev.micro.controller;

import com.google.common.collect.Lists;
import com.sa.dev.micro.JsonUtil;
import com.sa.dev.micro.TestApp;
import com.sa.dev.micro.entity.Account;
import com.sa.dev.micro.service.AccountService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sujitagarwal on 25/08/17.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest extends TestApp {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void create() throws Exception {
        Mockito.when(accountService.createAccount(getAccount())).thenReturn(getAccount());
        mvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(getAccount())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        List<Account> accountList = Lists.newArrayList();
        accountList.add(getAccount());
        Mockito.when(accountService.findAll()).thenReturn(accountList);
        mvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void byAccountNumber() throws Exception {
        Mockito.when(accountService.findByAccountNumber(getAccount().getAccountNumber())).thenReturn(getAccount());
        mvc.perform(get("/accounts/account/" + getAccount().getAccountNumber()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
