package com.yanchware.userservice.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("modules")
public class Module {
    @Id
    private long id;
    private long subscriptionId;
    private String type;
}
