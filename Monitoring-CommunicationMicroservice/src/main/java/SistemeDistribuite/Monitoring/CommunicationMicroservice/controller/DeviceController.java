package SistemeDistribuite.Monitoring.CommunicationMicroservice.controller;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Device;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.service.interfaces.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/device")
@CrossOrigin(origins = "http://localhost:4200")
public class DeviceController {
    private final Logger logger = Logger.getLogger(DeviceController.class.getName());
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Device> create(@PathVariable Integer id) {
        logger.info("Device created");
        return ResponseEntity.ok(deviceService.create(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Device> delete(@PathVariable Integer id) {
        logger.info("Device deleted");
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
