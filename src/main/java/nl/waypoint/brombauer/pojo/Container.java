package nl.waypoint.brombauer.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container {

    private String uuid;
    private String currentDateTime;
    private Double roundedDecimal;
    private StringBuilder calculationResult;
    private String hash;
}
