package model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@EqualsAndHashCode
@ToString
@JsonDeserialize
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Triangle implements Comparable<Triangle>{
    public String id;
    public Float firstSide;
    public Float secondSide;
    public Float thirdSide;

    public int compareTo(Triangle t) {
        if (id == null || t.id == null) {
            return 0;
        }
        return id.compareTo(t.id);
    }
}
