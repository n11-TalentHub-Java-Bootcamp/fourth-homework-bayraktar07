package com.bayraktar.springboot.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;

}
