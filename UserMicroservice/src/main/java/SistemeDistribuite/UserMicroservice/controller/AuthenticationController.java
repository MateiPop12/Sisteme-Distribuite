package SistemeDistribuite.UserMicroservice.controller;

import SistemeDistribuite.UserMicroservice.model.AuthenticationRequest;
import SistemeDistribuite.UserMicroservice.model.AuthenticationResponse;
import SistemeDistribuite.UserMicroservice.model.RegisterRequest;
import SistemeDistribuite.UserMicroservice.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final Logger logger = Logger.getLogger(AuthenticationController.class.getName());
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest){
        logger.info("Register called");
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
        logger.info("Authenticate called");
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }
}
