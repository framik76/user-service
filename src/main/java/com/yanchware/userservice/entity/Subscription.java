package com.yanchware.userservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Table("subscriptions")
public class Subscription {
    @Id
    private long id;
    private long userId;
    private Timestamp timestamp;
    private Timestamp expireDate;
}
