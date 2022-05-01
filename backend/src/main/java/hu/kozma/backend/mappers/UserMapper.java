package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.models.User;

public class UserMapper {
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
