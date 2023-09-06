package com.tn.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;

    private String userName;

    private String fullName;

    private String password;

    private String role;

    private Integer groupId;
}
