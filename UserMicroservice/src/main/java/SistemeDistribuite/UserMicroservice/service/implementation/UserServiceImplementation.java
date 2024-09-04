package SistemeDistribuite.UserMicroservice.service.implementation;

import SistemeDistribuite.UserMicroservice.data.entities.User;
import SistemeDistribuite.UserMicroservice.model.UpdateUserDto;
import SistemeDistribuite.UserMicroservice.model.UserDto;
import SistemeDistribuite.UserMicroservice.data.repository.RoleRepository;
import SistemeDistribuite.UserMicroservice.data.repository.UserRepository;
import SistemeDistribuite.UserMicroservice.service.interfaces.UserService;
import SistemeDistribuite.UserMicroservice.service.mappers.UserToUserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserToUserDtoMapper::converter).collect(Collectors.toList());
    }

    @Override
    public UserDto getByUsername(String username) {
        return UserToUserDtoMapper.converter(userRepository.findByUsername(username)
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

        return userRepository.save(user);
    }

    @Override
    public User update(UpdateUserDto updateUserDto) {
        User newUser = userRepository.findById(updateUserDto.getId())
                .orElseThrow(()->new UsernameNotFoundException("User not found")
                );
        newUser.setId(updateUserDto.getId());
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
        userRepository.deleteById(id);
    }
}