package com.yanchware.userservice.model;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionResponse {
    private long id;
    private long userId;
    private String creationDate;
    private String expireDate;
    private List<String> modules;
}
