package SistemeDistribuite.Monitoring.CommunicationMicroservice.service.implementations;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Device;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.repositories.DeviceRepository;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.service.interfaces.DeviceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImplementation  implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImplementation(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @RabbitListener(queues = "${rabbitmq.device.queue}")
    public void receiveMessage(int id) {
        if(id>0){
            create(id);
        }else{
            id*=-1;
            delete(id);
        }
    }

    @Override
    public Device create(int id) {
        if (deviceRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Device with id " + id + " already exists");
        } else if (id==0) {
            throw new IllegalArgumentException("Device with id equals 0 is forbidden");
        }

        Device device = new Device();
        device.setId(id);

        return deviceRepository.save(device);
    }

    @Override
    public void delete(int id) {
        deviceRepository.deleteById(id);
    }
}
