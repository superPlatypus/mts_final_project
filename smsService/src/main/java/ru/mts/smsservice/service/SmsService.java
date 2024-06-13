package ru.mts.smsservice.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class SmsService {

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    private final Map<String, String> verificationCodes = new HashMap<>();

    public void sendSms(String toPhoneNumber) {
        String verificationCode = generateVerificationCode();
        Message.creator(
                new PhoneNumber(toPhoneNumber),
                new PhoneNumber(fromPhoneNumber),
                "Your verification code is: " + verificationCode
        ).create();

        verificationCodes.put(toPhoneNumber, verificationCode);
    }

    public boolean verifyCode(String phoneNumber, String code) {
        return code.equals(verificationCodes.get(phoneNumber));
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}