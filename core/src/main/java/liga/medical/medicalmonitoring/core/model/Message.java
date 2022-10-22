package liga.medical.medicalmonitoring.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Message implements Serializable {
    @EqualsAndHashCode.Exclude
    private String id;
    @EqualsAndHashCode.Exclude
    private String text;
    private Status status;
}
