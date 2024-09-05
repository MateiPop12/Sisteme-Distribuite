package SistemeDistribuite.DeviceMicroservice.data.repository;

import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
