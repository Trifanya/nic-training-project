package dev.trifanya.server_app.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private int id;
    private String name;
    private String surname;
    private String position;
    private String email;
    private String password;
}
