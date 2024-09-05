package SistemeDistribuite.DeviceMicroservice.service.interfaces;

import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User create(User user);
    void delete(int id);
}
