package SistemeDistribuite.Monitoring.CommunicationMicroservice.service.implementations;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Device;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.repositories.DeviceRepository;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.service.interfaces.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImplementation  implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImplementation(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device createDevice(int id) {
        if (deviceRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Device with id " + id + " already exists");
        } else if (id<1) {
            throw new IllegalArgumentException("Device with id below 1 is forbidden");
        }

        Device device = new Device();
        device.setId(id);

        return deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(int id) {
        deviceRepository.deleteById(id);
    }
}
