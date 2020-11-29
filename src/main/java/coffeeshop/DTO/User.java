package coffeeshop.DTO;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private int role;
    private boolean status;
}

