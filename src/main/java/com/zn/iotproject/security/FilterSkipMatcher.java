package com.zn.iotproject.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FilterSkipMatcher implements RequestMatcher {
    private final OrRequestMatcher orRequestMatcher;
    private final RequestMatcher processingMatcher;

    public FilterSkipMatcher(List<String> pathToSkip, String processingPath) {
        this.orRequestMatcher = new OrRequestMatcher(pathToSkip.stream().map((p) -> {
            String method = p.split(" ")[0];
            String path = p.split(" ")[1];

            return new AntPathRequestMatcher(path, method);
        }).collect(Collectors.toList()));
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }

    @Override
    public boolean matches(HttpServletRequest req) {
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req);
    }
}
