package hu.kozma.backend.mappers;

import hu.kozma.backend.dto.RegisterDTO;
import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.model.User;

public class UserMapper {
	public static UserDTO toUserDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		return userDTO;
	}

	public static User toUser(RegisterDTO userDTO) {
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		return user;
	}
}
