package SistemeDistribuite.UserMicroservice.service.interfaces;

import SistemeDistribuite.UserMicroservice.data.entities.Role;
import SistemeDistribuite.UserMicroservice.model.RoleDto;

import java.util.List;

public interface RoleService {
    List<Role> getAll();
    Role create(RoleDto roleDto);
    void delete(int id);
}
