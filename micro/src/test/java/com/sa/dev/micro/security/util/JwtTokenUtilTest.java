package com.sa.dev.micro.security.util;

import com.google.common.collect.Lists;
import com.sa.dev.micro.security.authentication.model.AuthorityName;
import com.sa.dev.micro.security.authentication.model.JwtUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by sujitagarwal on 24/09/17.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenUtilTest {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void generateToken() throws Exception {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(AuthorityName.ROLE_ADMIN.name());
        SimpleGrantedAuthority simpleGrantedAuthority1 = new SimpleGrantedAuthority(AuthorityName.ROLE_USER.name());
        List<GrantedAuthority> list = Lists.newArrayList();
        list.add(simpleGrantedAuthority);
        list.add(simpleGrantedAuthority1);
        JwtUser jwtUser = new JwtUser(1L, "sujit.agarwal", "sujit", "agarwal", "sujit21agarwal@gmail.com", "test", list, true);
        String token = jwtTokenUtil.generateToken(jwtUser);
        // Thread.sleep(10001);
        System.out.println(token);
      /*  JwtUser userJ = jwtTokenUtil.parseToken(token);
        Assert.assertEquals(userJ.getId(), jwtUser.getId());
        Assert.assertEquals(userJ.getEmail(), jwtUser.getEmail());
        Assert.assertEquals(userJ.getFirstname(), jwtUser.getFirstname());
        Assert.assertEquals(userJ.getLastname(), jwtUser.getLastname());
        Assert.assertEquals(userJ.getAuthorities(), jwtUser.getAuthorities());
        Assert.assertEquals(userJ.getPassword(), jwtUser.getPassword());*/


    }


}
