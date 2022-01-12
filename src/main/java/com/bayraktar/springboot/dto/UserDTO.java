package com.bayraktar.springboot.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    private Long id;
    @Size(min = 2, max = 30)
    private String name;
    @Size(min = 2, max = 30)
    private String surname;
    @Pattern(regexp = "\\d{10}" ,message = "Incorrect entry. Correct format: 0001112233.")
    private String phoneNumber;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            +"[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            +"(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private String email;

}
