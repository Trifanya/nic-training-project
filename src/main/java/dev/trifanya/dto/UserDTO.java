package dev.trifanya.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class UserDTO {
    //private int id;

    private String name;

    private String surname;

    private String position;

    private String email;

    private String password;
}
