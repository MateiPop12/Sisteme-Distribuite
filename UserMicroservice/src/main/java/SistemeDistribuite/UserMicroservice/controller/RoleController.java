package SistemeDistribuite.UserMicroservice.controller;

import SistemeDistribuite.UserMicroservice.data.entities.Role;
import SistemeDistribuite.UserMicroservice.model.RoleDto;
import SistemeDistribuite.UserMicroservice.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/role")
@CrossOrigin(origins = {"http://localhost:4200", "http://frontend.localhost"})
public class RoleController {
    private final Logger logger = Logger.getLogger(RoleController.class.getName());
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAll() {
        logger.info("Get all roles called");
        return ResponseEntity.ok(roleService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Role> create(@RequestBody RoleDto roleDto) {
        logger.info("Create role called");
        return ResponseEntity.ok(roleService.create(roleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        logger.info("Delete role called");
        roleService.delete(id);
        return null;
    }
}
