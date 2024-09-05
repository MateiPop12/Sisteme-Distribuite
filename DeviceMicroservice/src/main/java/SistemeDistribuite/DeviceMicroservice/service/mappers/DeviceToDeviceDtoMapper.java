package SistemeDistribuite.DeviceMicroservice.service.mappers;

import SistemeDistribuite.DeviceMicroservice.data.entities.Device;
import SistemeDistribuite.DeviceMicroservice.model.DeviceDto;

public class DeviceToDeviceDtoMapper {
    public static DeviceDto converter(Device device) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(device.getId());
        deviceDto.setName(device.getName());
        deviceDto.setAddress(device.getAddress());
        deviceDto.setMaxConsumption(device.getMaxConsumption());
        deviceDto.setUser(device.getUser());
        return deviceDto;
    }
}
