package com.sa.dev.micro.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when token cannot be found in the request header
 *
 * @author sujit agarwal
 */

public class JwtTokenMissingException extends AuthenticationException {

    public JwtTokenMissingException(String msg) {
        super(msg);
    }
}
