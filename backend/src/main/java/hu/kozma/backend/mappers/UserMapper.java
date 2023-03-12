package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.model.User;

public class UserMapper {
    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}
