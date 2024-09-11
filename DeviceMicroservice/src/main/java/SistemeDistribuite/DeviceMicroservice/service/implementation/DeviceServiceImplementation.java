package SistemeDistribuite.DeviceMicroservice.service.implementation;

import SistemeDistribuite.DeviceMicroservice.data.entities.Device;
import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import SistemeDistribuite.DeviceMicroservice.data.repository.DeviceRepository;
import SistemeDistribuite.DeviceMicroservice.model.DeviceDto;
import SistemeDistribuite.DeviceMicroservice.service.interfaces.DeviceService;
import SistemeDistribuite.DeviceMicroservice.service.mappers.DeviceToDeviceDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImplementation implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImplementation(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<DeviceDto> getAll() {
        return deviceRepository.findAll().stream().map(DeviceToDeviceDtoMapper::converter).collect(Collectors.toList());
    }

    @Override
    public List<DeviceDto> getByUserId(User user) {
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
        return deviceRepository.save(device);
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
        deviceRepository.deleteById(id);
    }
}
