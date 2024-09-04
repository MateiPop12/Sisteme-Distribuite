package SistemeDistribuite.UserMicroservice.service.implementation;

import SistemeDistribuite.UserMicroservice.data.entities.Role;
import SistemeDistribuite.UserMicroservice.model.RoleDto;
import SistemeDistribuite.UserMicroservice.data.repository.RoleRepository;
import SistemeDistribuite.UserMicroservice.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImplementation implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Role create(RoleDto roleDto) {
        Role role = new Role();
        role.setName(roleDto.getRole());
        return roleRepository.save(role);
    }

    @Override
    public void delete(int id) {
        roleRepository.deleteById(id);
    }
}
