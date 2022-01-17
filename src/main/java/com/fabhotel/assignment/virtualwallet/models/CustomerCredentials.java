package com.fabhotel.assignment.virtualwallet.models;

import javax.validation.constraints.NotBlank;

public class CustomerCredentials {

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public CustomerCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
