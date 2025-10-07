package com.example.soho_bank.common;

public class UserCredentialsMissmatchException extends RuntimeException {
    public UserCredentialsMissmatchException(String userPasswordDoesNotMatch) {
        super(userPasswordDoesNotMatch);
    }
}
