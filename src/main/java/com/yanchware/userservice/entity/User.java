package com.yanchware.userservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("users")
public class User {

    @Id
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private String apiKey;
}
