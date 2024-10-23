package SistemeDistribuite.DeviceMicroservice.service.interfaces;

import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    List<User> all();
    User create(int id);
    void delete(int id);
}
