package SistemeDistribuite.Monitoring.CommunicationMicroservice.service.interfaces;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Reading;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.model.ReadingDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReadingService {
    List<ReadingDto> getReadingsByDeviceId(int id);
    Reading create(ReadingDto readingDto);
}
