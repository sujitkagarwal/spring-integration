package com.sa.dev.micro.security.authentication.provider;


import com.sa.dev.micro.security.authentication.exception.InvalidTokenException;
import com.sa.dev.micro.security.authentication.exception.JwtTokenMalformedException;
import com.sa.dev.micro.security.authentication.model.JwtAuthenticationToken;
import com.sa.dev.micro.security.authentication.model.JwtUser;
import com.sa.dev.micro.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Used for checking the token from the request and supply the UserDetails if the token is valid
 *
 * @author pascal alma
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

      @Autowired
      private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }


   /* @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();
        JwtUser parsedUser=null;
        try {
            parsedUser = jwtTokenUtil.parseToken(token);
        }catch (InvalidTokenException e) {
            throw new JwtTokenMalformedException(e.getMessage());
        }
        jwtAuthenticationToken.setAuthenticated(true);
        return jwtAuthenticationToken;
    }
*/
     @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();
        JwtUser parsedUser=null;
        try {
            parsedUser = jwtTokenUtil.parseToken(token);
        }catch (InvalidTokenException e) {
            throw new JwtTokenMalformedException(e.getMessage());
        }
        return parsedUser;
    }

}
