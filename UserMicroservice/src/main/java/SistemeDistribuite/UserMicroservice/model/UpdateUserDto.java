package SistemeDistribuite.UserMicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserDto {
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
}
