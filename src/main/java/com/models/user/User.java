package com.models.user;

import lombok.Data;

@Data
public class User {
    String name;
    String email;
    String imageUrl;
    EmailServer emailServer;
    String token;
}
