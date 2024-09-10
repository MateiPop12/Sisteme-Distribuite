package SistemeDistribuite.UserMicroservice.controller;

import SistemeDistribuite.UserMicroservice.model.AuthenticationRequest;
import SistemeDistribuite.UserMicroservice.model.AuthenticationResponse;
import SistemeDistribuite.UserMicroservice.model.RegisterRequest;
import SistemeDistribuite.UserMicroservice.service.interfaces.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final Logger logger = Logger.getLogger(AuthenticationController.class.getName());
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

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
