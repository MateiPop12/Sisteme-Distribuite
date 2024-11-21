package SistemeDistribuite.Monitoring.CommunicationMicroservice.controller;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Reading;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.model.ReadingDto;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.service.interfaces.ReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/reading")
@CrossOrigin(origins = {"http://localhost:4200", "http://frontend.localhost"})
public class ReadingController {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ReadingService readingService;

    @Autowired
    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<ReadingDto>> getAllByDeviceId(@PathVariable int id) {
        logger.info("Get all readings by Device id: " + id);
        return ResponseEntity.ok(readingService.getReadingsByDeviceId(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Reading> create(@RequestBody ReadingDto readingDto) {
        logger.info("Created reading");
        return ResponseEntity.ok(readingService.create(readingDto));
    }
}
