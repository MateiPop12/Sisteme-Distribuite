package SistemeDistribuite.Monitoring.CommunicationMicroservice.data.repositories;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Device;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Reading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Integer> {
    List<Reading> findReadingsByDevice(Device device);
}
