package nl.waypoint.brombauer.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@ToString
public class Container implements Serializable {

    private String uuid;
    private String currentDateTime;
    private Double roundedDecimal;
    private StringBuilder calculationResult;
    private String hash;
}
