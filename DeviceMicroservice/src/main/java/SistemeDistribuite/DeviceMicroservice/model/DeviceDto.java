package SistemeDistribuite.DeviceMicroservice.model;

import SistemeDistribuite.DeviceMicroservice.data.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeviceDto {
    private int id;
    private String name;
    private String address;
    private double maxConsumption;
    private int userId;
}
