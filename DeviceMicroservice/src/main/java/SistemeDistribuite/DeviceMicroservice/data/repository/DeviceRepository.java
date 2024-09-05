package SistemeDistribuite.DeviceMicroservice.data.repository;

import SistemeDistribuite.DeviceMicroservice.data.entities.Device;
import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> findDevicesByUser(User user);
}
