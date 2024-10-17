package SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "readings")
public class Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "measurement_value")
    private Double measurement_value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device")
    private Device device;
}
