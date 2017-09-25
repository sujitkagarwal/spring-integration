package com.sa.dev.micro.security.matcher;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is created by for path ro skip
 *
 * @author sujit kumar agarwal
 */
public class SkipPathRequestMatcher implements RequestMatcher {
    private final OrRequestMatcher orRequestMatcher;

    public SkipPathRequestMatcher(List<String> pathToSkip) {
        List<RequestMatcher> requestMatchers = pathToSkip.stream().map(AntPathRequestMatcher::new).collect(Collectors.toList());
        orRequestMatcher = new OrRequestMatcher(requestMatchers);
    }

    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {
        return !orRequestMatcher.matches(httpServletRequest);
    }
}
