package com.sa.dev.micro.security.util;

import com.google.common.collect.Lists;
import com.sa.dev.micro.security.authentication.exception.InvalidTokenException;
import com.sa.dev.micro.security.authentication.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {

    static final String CLAIM_KEY_ID = "id";
    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_FNAME = "f_sub";
    static final String CLAIM_KEY_LNAME = "L_sub";
    static final String CLAIM_KEY_pass = "sub_pass";
    static final String CLAIM_KEY_email = "sub_email";
    static final String CLAIM_KEY_ROLE = "role";
    static final String CLAIM_KEY_STATUS = "status";
    static final String CLAIM_KEY_CREATED = "created";
    private static final long serialVersionUID = -3301605591108950415L;
    @Autowired
    private TimeProvider timeProvider;

    @Value("${com.sa.dev.micro.secret}")
    private String secret;

    @Value("${com.sa.dev.micro.expiration}" )
    private String expiration;


    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Boolean isTokenExpired(String token) throws InvalidTokenException {

        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            throw new InvalidTokenException("Token expired");
        }
        return expiration.before(timeProvider.now());
    }

    public String generateToken(JwtUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_ID, userDetails.getId());
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_FNAME, userDetails.getFirstname());
        claims.put(CLAIM_KEY_LNAME, userDetails.getLastname());
        claims.put(CLAIM_KEY_pass, userDetails.getPassword());
        claims.put(CLAIM_KEY_email, userDetails.getEmail());
        claims.put(CLAIM_KEY_STATUS, userDetails.isEnabled());
        final Date createdDate = timeProvider.now();
        claims.put(CLAIM_KEY_CREATED, createdDate);
        claims.put(CLAIM_KEY_ROLE, userDetails.getAuthorities());
        return doGenerateToken(claims);
    }

    private String doGenerateToken(Map<String, Object> claims) {
        final Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
        final Date expirationDate = new Date(createdDate.getTime() + Long.parseLong(expiration) * 1000);
         return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public JwtUser parseToken(String token) throws InvalidTokenException {
        JwtUser user = null;
        List<GrantedAuthority> list = Lists.newArrayList();
        try {
            if (!isTokenExpired(token)) {
                final Claims claims = getClaimsFromToken(token);
                final long id = (Integer) claims.get(CLAIM_KEY_ID);
                final String userName = (String) claims.get(CLAIM_KEY_USERNAME);
                final String fName = (String) claims.get(CLAIM_KEY_FNAME);
                final String lName = (String) claims.get(CLAIM_KEY_LNAME);
                final String pass = (String) claims.get(CLAIM_KEY_pass);
                final String email = (String) claims.get(CLAIM_KEY_email);
                final Boolean status = (Boolean) claims.get(CLAIM_KEY_STATUS);
                final List<HashMap<String, String>> grantedAuthorityList = (List) claims.get(CLAIM_KEY_ROLE);
                for (HashMap<String, String> granMap : grantedAuthorityList) {
                    granMap.forEach((k, v) -> list.add(new SimpleGrantedAuthority(v)));
                }
                user = new JwtUser(id, userName, fName, lName, email, pass, list, status);
            } else {
                throw new InvalidTokenException("Token Expired");
            }
        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }

        return user;
    }
}