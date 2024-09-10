package SistemeDistribuite.UserMicroservice.service.mappers;

import SistemeDistribuite.UserMicroservice.data.entities.User;
import SistemeDistribuite.UserMicroservice.model.UpdateUserDto;

public class UserToUpdateUserDtoMapper {
    public static UpdateUserDto converter(User user) {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setId(user.getId());
        updateUserDto.setUsername(user.getUsername());
        updateUserDto.setEmail(user.getEmail());
        updateUserDto.setPassword(user.getPassword());
        updateUserDto.setRole(user.getRole().getName());

        return updateUserDto;
    }
}
