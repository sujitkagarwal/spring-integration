package com.sa.dev.micro.integration;


import com.sa.dev.micro.JsonUtil;
import com.sa.dev.micro.TestApp;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerIntegrationTest extends TestApp {

    @Autowired
    private MockMvc mvc;

    @Test
    public void create() throws Exception {
        mvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(getAccount())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("sujit")));


    }


    @Test
    public void findAll() throws Exception {
        mvc.perform(get("/accounts").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));
        //   .andExpect(jsonPath("$[0].name", is(alex.getName())))
        // .andExpect(jsonPath("$[1].name", is(john.getName())))
        //.andExpect(jsonPath("$[2].name", is(bob.getName())));

    }

    @Test
    public void byAccountNumber() throws Exception {
        mvc.perform(get("/accounts/account/" + getAccount().getAccountNumber()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("sujit")));


    }
}