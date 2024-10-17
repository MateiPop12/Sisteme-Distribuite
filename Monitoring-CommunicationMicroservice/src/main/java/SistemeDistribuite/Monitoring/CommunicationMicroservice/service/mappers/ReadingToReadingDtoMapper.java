package SistemeDistribuite.Monitoring.CommunicationMicroservice.service.mappers;

import SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities.Reading;
import SistemeDistribuite.Monitoring.CommunicationMicroservice.model.ReadingDto;

public class ReadingToReadingDtoMapper {
    public static ReadingDto converter(Reading reading){
        ReadingDto readingDto = new ReadingDto();
        readingDto.setTimestamp(reading.getTimestamp());
        readingDto.setMeasurement_value(reading.getMeasurement_value());
        readingDto.setDeviceId(reading.getDevice().getId());
        return readingDto;
    }

}
