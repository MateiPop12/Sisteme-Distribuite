package SistemeDistribuite.UserMicroservice.service.interfaces;

import SistemeDistribuite.UserMicroservice.data.entities.User;
import SistemeDistribuite.UserMicroservice.model.UpdateUserDto;
import SistemeDistribuite.UserMicroservice.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    List<UserDto> getAll();
    UserDto getByUsername(String username);
    User create(UserDto userDto);
    User update(UpdateUserDto userDto);
    void delete(int id);
}
