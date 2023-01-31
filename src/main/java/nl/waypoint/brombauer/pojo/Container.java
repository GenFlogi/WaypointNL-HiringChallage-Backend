package nl.waypoint.brombauer.pojo;

import lombok.*;

import java.io.Serializable;

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
