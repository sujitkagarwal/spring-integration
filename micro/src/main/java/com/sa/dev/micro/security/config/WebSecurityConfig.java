package com.sa.dev.micro.security.config;


import com.sa.dev.micro.security.authentication.handler.JwtAuthenticationFailureHandler;
import com.sa.dev.micro.security.authentication.provider.JwtAuthenticationProvider;
import com.sa.dev.micro.security.filter.JwtAuthenticationTokenFilter;
import com.sa.dev.micro.security.matcher.SkipPathRequestMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * This class is used to handle the security.
 *
 * @author sujit agarwal
 */
@ConditionalOnProperty("com.sa.dev.micro.security")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final String[] pathToSkips = {"/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**"};

    @Value("${com.sa.dev.micro.header}")
    private String tokenHeader;

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(this.jwtAuthenticationProvider);
    }


    /*@Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Arrays.asList(authenticationProvider));
    }
*/

    public JwtAuthenticationTokenFilter authenticationTokenFilter() throws Exception {
        List<String> pathtoSkipList = Arrays.asList(pathToSkips);
        SkipPathRequestMatcher skipPathRequestMatcher = new SkipPathRequestMatcher(pathtoSkipList);
        JwtAuthenticationTokenFilter authenticationTokenFilter = new JwtAuthenticationTokenFilter(jwtAuthenticationFailureHandler, skipPathRequestMatcher, tokenHeader);
        // authenticationTokenFilter.setAuthenticationManager(authenticationManager());
        authenticationTokenFilter.setAuthenticationManager(authenticationManager);
        return authenticationTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                .exceptionHandling()//.authenticationEntryPoint(unauthorizedHandler)
                .and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //.and()
        // Custom JWT based security filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilter(), BasicAuthenticationFilter.class);

        // disable page caching
        httpSecurity.headers().cacheControl();
    }
}
