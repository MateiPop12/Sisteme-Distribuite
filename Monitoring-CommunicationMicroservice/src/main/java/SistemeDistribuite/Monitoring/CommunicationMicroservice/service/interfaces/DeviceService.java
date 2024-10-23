package SistemeDistribuite.Monitoring.CommunicationMicroservice.service.interfaces;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Device;
import org.springframework.stereotype.Component;

@Component
public interface DeviceService {
    Device create(int id);
    void delete(int id);
}
