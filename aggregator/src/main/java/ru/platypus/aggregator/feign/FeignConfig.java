package ru.platypus.aggregator.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.platypus.aggregator.service.JwtCookieService;


@Configuration
public class FeignConfig {

    @Autowired
    private JwtCookieService jwtCookieService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                String jwtToken = jwtCookieService.getJwtTokenFromCookie();
                if (jwtToken != null) {
                    requestTemplate.header("Authorization", "Bearer " + jwtToken);
                }
            }
        };
    }
}
