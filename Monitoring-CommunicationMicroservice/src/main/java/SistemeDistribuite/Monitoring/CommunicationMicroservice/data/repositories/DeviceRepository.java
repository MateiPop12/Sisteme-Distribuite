package SistemeDistribuite.Monitoring.CommunicationMicroservice.data.repositories;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Optional<Device> findById(int id);
}
