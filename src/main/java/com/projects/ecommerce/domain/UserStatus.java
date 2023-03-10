package com.projects.ecommerce.domain;

public enum UserStatus {
    NOT_LOGGED_IN(0),
    LOGGED_IN(1),
    BLOCKED(2);
    private int status;
    UserStatus(int status) {
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}