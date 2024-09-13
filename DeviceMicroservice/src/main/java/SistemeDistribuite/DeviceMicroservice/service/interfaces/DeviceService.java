package SistemeDistribuite.DeviceMicroservice.service.interfaces;

import SistemeDistribuite.DeviceMicroservice.data.entities.Device;
import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import SistemeDistribuite.DeviceMicroservice.model.DeviceDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceService {
    List<DeviceDto> getAll();
    List<DeviceDto> getByUserId(Integer id);
    Device create(DeviceDto device);
    Device update(DeviceDto device);
    void delete(int id);
}
