package SistemeDistribuite.DeviceMicroservice.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "max_consumption", nullable = false, unique = true)
    private int maxConsumption;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
