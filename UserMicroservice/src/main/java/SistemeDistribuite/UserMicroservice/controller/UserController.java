package SistemeDistribuite.UserMicroservice.controller;

import SistemeDistribuite.UserMicroservice.model.UpdateUserDto;
import SistemeDistribuite.UserMicroservice.model.UserDto;
import SistemeDistribuite.UserMicroservice.data.entities.User;
import SistemeDistribuite.UserMicroservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:4200", "http://frontend.localhost"})
public class UserController {
    private final Logger logger = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UpdateUserDto>> getAll() {
        logger.info("Get all users");
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UpdateUserDto> getByUsername(@PathVariable String username) {
        logger.info("Get by username called ");
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        logger.info("Create user called");
        return ResponseEntity.ok(userService.create(userDto));
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody UpdateUserDto updateUserDto) {
        logger.info("Update user called");
        return ResponseEntity.ok(userService.update(updateUserDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        logger.info("User deleted with id " + id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
