package SistemeDistribuite.Monitoring.CommunicationMicroservice.service.interfaces;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Device;
import org.springframework.stereotype.Component;

@Component
public interface DeviceService {
    Device createDevice(int id);
    void deleteDevice(int id);
}
