package com.yanchware.userservice.model;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionModelRequest {

    private long userId;
    private String expireDate;
    private List<String> modules;
}
