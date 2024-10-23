package SistemeDistribuite.Monitoring.CommunicationMicroservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

@Getter
@Setter
@NoArgsConstructor
public class ReceivedReadingDto {
    private String timestamp;
    private Double value;
    private int device_id;

    public Timestamp convert(String timestamp) {
        try {
            // Parse the timestamp string
            OffsetDateTime offsetDateTime = OffsetDateTime.parse(timestamp);

            // Convert to Instant and then to Timestamp
            Instant instant = offsetDateTime.toInstant();
            return Timestamp.from(instant);
        } catch (DateTimeParseException | NullPointerException | IllegalArgumentException e) {
            // Handle parsing exceptions
            throw new IllegalArgumentException("Invalid timestamp format", e);
        }
    }
    @Override
    public String toString() {
        return "ReceivedReadingDto{" +
                "timestamp='" + timestamp + '\'' +
                ", measurement_value=" + value +
                ", deviceId=" + device_id +
                '}';
    }

}
