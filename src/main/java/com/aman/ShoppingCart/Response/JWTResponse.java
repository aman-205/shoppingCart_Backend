package com.aman.ShoppingCart.Response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class JWTResponse {

    private Long id;
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public JWTResponse(Long id, String token) {
        this.id = id;
        this.token = token;
    }
}
