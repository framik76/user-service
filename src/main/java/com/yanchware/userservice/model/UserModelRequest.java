package com.yanchware.userservice.model;

import lombok.Data;

@Data
public class UserModelRequest {
    private String email;
    private String firstName;
    private String lastName;
}
