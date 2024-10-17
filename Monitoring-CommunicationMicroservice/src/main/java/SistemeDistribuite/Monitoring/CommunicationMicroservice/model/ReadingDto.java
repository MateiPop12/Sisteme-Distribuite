package SistemeDistribuite.Monitoring.CommunicationMicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ReadingDto {
    private Timestamp timestamp;
    private Double measurement_value;
    private int deviceId;
}
