package SistemeDistribuite.DeviceMicroservice.service.implementation;

import SistemeDistribuite.DeviceMicroservice.data.entities.Device;
import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import SistemeDistribuite.DeviceMicroservice.data.repository.DeviceRepository;
import SistemeDistribuite.DeviceMicroservice.model.DeviceDto;
import SistemeDistribuite.DeviceMicroservice.service.interfaces.DeviceService;
import SistemeDistribuite.DeviceMicroservice.service.mappers.DeviceToDeviceDtoMapper;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImplementation implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String EXCHANGE_NAME;
    @Value("${rabbitmq.device.routing-key}")
    private String ROUTING_KEY;

    @Autowired
    public DeviceServiceImplementation(DeviceRepository deviceRepository, RabbitTemplate rabbitTemplate) {
        this.deviceRepository = deviceRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<DeviceDto> getAll() {
        return deviceRepository.findAll().stream().map(DeviceToDeviceDtoMapper::converter).collect(Collectors.toList());
    }

    @Override
    public List<DeviceDto> getByUserId(Integer id) {
        User user = new User();
        user.setId(id);
        return deviceRepository.findDevicesByUser(user).stream().map(DeviceToDeviceDtoMapper::converter).collect(Collectors.toList());
    }

    @Override
    public Device create(DeviceDto deviceDto) {
        Device device = new Device();
        User user = new User();
        user.setId(deviceDto.getUserId());
        device.setName(deviceDto.getName());
        device.setAddress(deviceDto.getAddress());
        device.setMaxConsumption(deviceDto.getMaxConsumption());
        device.setUser(user);

        Device savedDevice = deviceRepository.save(device);
        int deviceId = savedDevice.getId();
        try {
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, deviceId);
        }catch (AmqpException e){
            deviceRepository.deleteById(deviceId);
            throw new RuntimeException("Failed to send message to RabbitMQ. Device has been rolled back.", e);
        }
        return savedDevice;
    }

    @Override
    public Device update(DeviceDto deviceDto) {
        Device newDevice = deviceRepository.findById(deviceDto.getId())
                .orElseThrow(()->new UsernameNotFoundException("Device not found")
        );
        User user = new User();
        user.setId(deviceDto.getUserId());

        newDevice.setName(deviceDto.getName());
        newDevice.setAddress(deviceDto.getAddress());
        newDevice.setMaxConsumption(deviceDto.getMaxConsumption());
        newDevice.setUser(user);
        return deviceRepository.save(newDevice);
    }

    @Override
    public void delete(int id) {
        try{
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, id*-1);
        } catch (AmqpException e){
            throw new RuntimeException("Failed to send message to RabbitMQ. Device has been rolled back.", e);
        }
        deviceRepository.deleteById(id);
    }
}
