package com.aman.ShoppingCart.Response;

public class APIResponse {

    private String message;
    private Object data;

    public APIResponse() {}

    public APIResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
