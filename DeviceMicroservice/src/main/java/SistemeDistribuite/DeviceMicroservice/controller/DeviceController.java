package SistemeDistribuite.DeviceMicroservice.controller;

import SistemeDistribuite.DeviceMicroservice.data.entities.Device;
import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import SistemeDistribuite.DeviceMicroservice.model.DeviceDto;
import SistemeDistribuite.DeviceMicroservice.service.interfaces.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/device")
@CrossOrigin(origins = "http://localhost4200")
public class DeviceController {

    private final Logger logger = Logger.getLogger(DeviceController.class.getName());
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeviceDto>> getAll() {
        logger.info("Get all devices");
        return ResponseEntity.ok(deviceService.getAll());
    }

    @GetMapping("/all/user")
    public ResponseEntity<List<DeviceDto>> getAllByUser(@RequestBody User user) {
        logger.info("Get all devices by user");
        return ResponseEntity.ok(deviceService.getByUserId(user));
    }

    @PostMapping("/create")
    public ResponseEntity<Device> create(@RequestBody DeviceDto deviceDto) {
        logger.info("Create device");
        return ResponseEntity.ok(deviceService.create(deviceDto));
    }

    @PutMapping("/update")
    public ResponseEntity<Device> update(@RequestBody DeviceDto deviceDto) {
        logger.info("Update device");
        return ResponseEntity.ok(deviceService.update(deviceDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Device> delete(@PathVariable Integer id) {
        logger.info("Delete device");
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
