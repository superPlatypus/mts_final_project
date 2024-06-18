package ru.mts.customerservice.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;


@Data
@RequiredArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private int id;
    private String username;
    private List<String> roles;

    public JwtResponse(String accessToken, int id, String username,List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}
