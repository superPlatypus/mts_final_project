package ru.platypus.aggregator.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import ru.platypus.aggregator.dto.Signin;
import ru.platypus.aggregator.feign.CustomerServiceFeignClient;
import ru.platypus.aggregator.feign.SmsServiceFeignClient;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final SmsServiceFeignClient smsServiceFeignClient;

    public AuthController(CustomerServiceFeignClient customerServiceFeignClient, SmsServiceFeignClient smsServiceFeignClient) {
        this.customerServiceFeignClient = customerServiceFeignClient;
        this.smsServiceFeignClient = smsServiceFeignClient;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("signin", new Signin());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("signin") Signin signin, HttpServletResponse response, Model model) {
//        var jwt = customerServiceFeignClient.signin(signin);
//        System.out.println(jwt.getBody());
        model.addAttribute("phoneNumber", signin.getPhoneNumber());
        smsServiceFeignClient.send(signin.getPhoneNumber());
        System.out.println("КОД ОТПРАВЛЕН!!!!");
//        ResponseCookie cookie = ResponseCookie.from("jwtToken", jwt.getBody())
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(7 * 24 * 60 * 60) // 7 days
//                .build();

//        response.addHeader("Set-Cookie", cookie.toString());
        return "loginVerify";
    }

    @PostMapping("login/verify")
    public String verify(@ModelAttribute("signin") Signin signin, @RequestParam String code, HttpServletResponse response) {
        var jwt = customerServiceFeignClient.signin(signin);
        System.out.println(jwt);
        ResponseEntity<String> isValid = smsServiceFeignClient.verify(signin.getPhoneNumber(), code);
        if (isValid.getStatusCode() == HttpStatus.OK) {
            ResponseCookie cookie = ResponseCookie.from("jwtToken", jwt.getBody())
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .maxAge(7 * 24 * 60 * 60) // 7 days
                    .build();
            response.addHeader("Set-Cookie", cookie.toString());
            return "redirect:/";
        }
        else{
            return "login";
        }
    }
}
