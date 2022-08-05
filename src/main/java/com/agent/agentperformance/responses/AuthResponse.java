package com.agent.agentperformance.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String userName;
    private String accessToken;

    private String id;

    private String role;



    public AuthResponse() { }

    public AuthResponse(String userName, String accessToken, String id, String role) {
        this.userName = userName;
        this.accessToken = accessToken;
        this.id = id;
        this.role = role;
    }

}