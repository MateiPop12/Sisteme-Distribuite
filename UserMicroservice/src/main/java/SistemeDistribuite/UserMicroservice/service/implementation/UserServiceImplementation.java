package SistemeDistribuite.UserMicroservice.service.implementation;

import SistemeDistribuite.UserMicroservice.data.entities.User;
import SistemeDistribuite.UserMicroservice.model.UpdateUserDto;
import SistemeDistribuite.UserMicroservice.model.UserDto;
import SistemeDistribuite.UserMicroservice.data.repository.RoleRepository;
import SistemeDistribuite.UserMicroservice.data.repository.UserRepository;
import SistemeDistribuite.UserMicroservice.service.interfaces.UserService;
import SistemeDistribuite.UserMicroservice.service.mappers.UserToUpdateUserDtoMapper;
import SistemeDistribuite.UserMicroservice.service.mappers.UserToUserDtoMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String EXCHANGE_NAME;
    @Value("${rabbitmq.routing-key}")
    private String ROUTING_KEY;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<UpdateUserDto> getAll() {
        return userRepository.findAll().stream().map(UserToUpdateUserDtoMapper::converter).collect(Collectors.toList());
    }

    @Override
    public UpdateUserDto getByUsername(String username) {
        return UserToUpdateUserDtoMapper.converter(userRepository.findByUsername(username)
                        .orElseThrow(()->new UsernameNotFoundException("User not found"))
        );
    }

    @Override
    public User create(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(roleRepository.findByName(userDto.getRole())
                .orElseThrow(()->new UsernameNotFoundException("Role not found"))
        );

        User savedUser = userRepository.save(user);
        int userId = savedUser.getId();
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, userId);
        } catch (AmqpException e) {
            userRepository.deleteById(userId);
            throw new RuntimeException("Failed to send message to RabbitMQ. User has been rolled back.", e);
        }

        return savedUser;
    }

    @Override
    public User update(UpdateUserDto updateUserDto) {
        User newUser = userRepository.findById(updateUserDto.getId())
                .orElseThrow(()->new UsernameNotFoundException("User not found")
                );
        newUser.setUsername(updateUserDto.getUsername());
        newUser.setEmail(updateUserDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        newUser.setRole(roleRepository.findByName(updateUserDto.getRole())
                .orElseThrow(()->new UsernameNotFoundException("Role not found"))
        );

        return userRepository.save(newUser);
    }

    @Override
    public void delete(int id) {
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, id * -1);
        } catch (AmqpException e) {
            throw new RuntimeException("Failed to send message to RabbitMQ. User has been rolled back.", e);
        }
        userRepository.deleteById(id);
    }
}
