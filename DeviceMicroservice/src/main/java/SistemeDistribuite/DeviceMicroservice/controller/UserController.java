package SistemeDistribuite.DeviceMicroservice.controller;

import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import SistemeDistribuite.DeviceMicroservice.service.interfaces.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<User> create(@PathVariable Integer id) {
        logger.info("User created");
        return ResponseEntity.ok(userService.create(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        logger.info("User deleted with id " + id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
