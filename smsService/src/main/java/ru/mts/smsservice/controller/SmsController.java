package ru.mts.smsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import ru.mts.api.ApiApi;
import ru.mts.smsservice.service.SmsService;

import java.util.Optional;

@RestController
public class SmsController implements ApiApi {


    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ApiApi.super.getRequest();
    }

    @Override
    public ResponseEntity<String> apiSmsSendPost(String phoneNumber) {
        smsService.sendSms(phoneNumber);
        return ResponseEntity.ok("SMS sent successfully");
    }

    @Override
    public ResponseEntity<String> apiSmsVerifyPost(String phoneNumber, String code) {
        boolean isValid = smsService.verifyCode(phoneNumber, code);
        if (isValid) {
            return ResponseEntity.ok("Verification successful");
        } else {
            return ResponseEntity.status(400).body("Invalid verification code");
        }
    }



}