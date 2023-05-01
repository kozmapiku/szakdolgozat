package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.LoginDTO;
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

    public static LoginDTO toLoginDTO(User user) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(user.getEmail());
        loginDTO.setFirstName(user.getFirstName());
        loginDTO.setLastName(user.getLastName());
        return loginDTO;
    }
}
