package SistemeDistribuite.UserMicroservice.service.mappers;

import SistemeDistribuite.UserMicroservice.model.UserDto;
import SistemeDistribuite.UserMicroservice.data.entities.User;

public class UserToUserDtoMapper {
    public static UserDto converter(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRole(user.getRole().getName());

        return userDto;
    }
}
