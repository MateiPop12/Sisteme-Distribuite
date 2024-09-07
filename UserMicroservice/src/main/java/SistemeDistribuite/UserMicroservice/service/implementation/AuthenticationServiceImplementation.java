package SistemeDistribuite.UserMicroservice.service.implementation;

import SistemeDistribuite.UserMicroservice.data.entities.User;
import SistemeDistribuite.UserMicroservice.model.AuthenticationRequest;
import SistemeDistribuite.UserMicroservice.model.AuthenticationResponse;
import SistemeDistribuite.UserMicroservice.model.RegisterRequest;
import SistemeDistribuite.UserMicroservice.data.repository.RoleRepository;
import SistemeDistribuite.UserMicroservice.data.repository.UserRepository;
import SistemeDistribuite.UserMicroservice.service.interfaces.AuthenticationService;
import SistemeDistribuite.UserMicroservice.service.security.JwtService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.NoSuchElementException;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String EXCHANGE_NAME;
    @Value("${rabbitmq.routing-key}")
    private String ROUTING_KEY;

    @Autowired
    public AuthenticationServiceImplementation(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.rabbitTemplate = rabbitTemplate;
    }

    public AuthenticationResponse register(RegisterRequest registerRequest) {

        var user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(roleRepository.findByName("USER").orElseThrow(
                () -> new NoSuchElementException("No role found")));
        User savedUser = userRepository.save(user);
        int userId = savedUser.getId();
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, userId);

        return new AuthenticationResponse(
                jwtService.generateToken(user)
        );
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword())
        );

        return new AuthenticationResponse(
                jwtService.generateToken(
                        userRepository.findByUsername(
                                authenticationRequest.getUsername()
                        ).orElseThrow(() -> new NoSuchElementException("No username:" + authenticationRequest.getUsername() + "found"))
                )
        );
    }
}
