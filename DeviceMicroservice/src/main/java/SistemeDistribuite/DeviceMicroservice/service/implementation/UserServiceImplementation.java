package SistemeDistribuite.DeviceMicroservice.service.implementation;

import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import SistemeDistribuite.DeviceMicroservice.data.repository.UserRepository;
import SistemeDistribuite.DeviceMicroservice.service.interfaces.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = {"${rabbitmq.queue}"})
    public void receiveMessage(int id) {
        if(id>0){
            create(id);
        }else{
            id*=-1;
            delete(id);
        }
    }

    @Override
    public User create(int id) {
        User user = new User();
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }
}
