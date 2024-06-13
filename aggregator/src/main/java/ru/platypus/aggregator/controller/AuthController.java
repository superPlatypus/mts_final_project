package ru.platypus.aggregator.controller;


import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import ru.platypus.aggregator.dto.Signin;
import ru.platypus.aggregator.feign.CustomerServiceFeignClient;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    private final CustomerServiceFeignClient customerServiceFeignClient;

    public AuthController(CustomerServiceFeignClient customerServiceFeignClient) {
        this.customerServiceFeignClient = customerServiceFeignClient;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("signin", new Signin());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("signin") Signin signin, HttpServletResponse response) {
        var jwt = customerServiceFeignClient.signin(signin);
        System.out.println(jwt.getBody());
        ResponseCookie cookie = ResponseCookie.from("jwtToken", jwt.getBody())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7 days
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
        return "redirect:/";
    }
}
