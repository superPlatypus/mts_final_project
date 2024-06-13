package ru.platypus.aggregator.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import javax.servlet.http.Cookie;

@Service
public class JwtCookieService {

    private final HttpServletRequest request;

    public JwtCookieService(HttpServletRequest request) {
        this.request = request;
    }

    public String getJwtTokenFromCookie() {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> "jwtToken".equals(cookie.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;
    }
}