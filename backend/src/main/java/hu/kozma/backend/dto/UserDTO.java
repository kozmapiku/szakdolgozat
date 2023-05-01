package hu.kozma.backend.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String firstName;
    private String lastName;
    @NonNull
    private String email;
    private String password;

    private String currentPassword;
    private String newPassword;

}
