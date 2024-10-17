package SistemeDistribuite.Monitoring.CommunicationMicroservice.service.implementations;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Device;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Reading;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.repositories.DeviceRepository;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.repositories.ReadingRepository;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.model.ReadingDto;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.service.interfaces.ReadingService;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.service.mappers.ReadingToReadingDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadingServiceImplementation implements ReadingService {

    private final ReadingRepository readingRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public ReadingServiceImplementation(ReadingRepository readingRepository, DeviceRepository deviceRepository) {
        this.readingRepository = readingRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<ReadingDto> getReadingsByDeviceId(int id) {
        Device device = deviceRepository.findById(id).orElseThrow(()->new RuntimeException("Device not found"));
        return readingRepository.findReadingsByDevice(device).stream().map(ReadingToReadingDtoMapper::converter).collect(Collectors.toList());
    }

    @Override
    public Reading create(ReadingDto readingDto) {
        Device device = deviceRepository.findById(readingDto.getDeviceId()).orElseThrow(()->new RuntimeException("Device not found"));
        Reading newReading = new Reading();
        newReading.setTimestamp(readingDto.getTimestamp());
        newReading.setMeasurement_value(readingDto.getMeasurement_value());
        newReading.setDevice(device);
        return readingRepository.save(newReading);
    }
}
