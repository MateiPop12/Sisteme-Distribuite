package SistemeDistribuite.Monitoring.CommunicationMicroservice.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "devices")
public class Device {
    @Id
    private int id;

}
