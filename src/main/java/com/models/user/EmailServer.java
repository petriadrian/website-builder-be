package com.models.user;

import lombok.Data;

@Data
public class EmailServer {
    String host;
    String port;
    String password;
}
