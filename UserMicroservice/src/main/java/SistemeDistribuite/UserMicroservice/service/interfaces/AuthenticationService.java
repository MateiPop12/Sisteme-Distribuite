package SistemeDistribuite.UserMicroservice.service.interfaces;

import SistemeDistribuite.UserMicroservice.model.AuthenticationRequest;
import SistemeDistribuite.UserMicroservice.model.AuthenticationResponse;
import SistemeDistribuite.UserMicroservice.model.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest registerRequest);
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
