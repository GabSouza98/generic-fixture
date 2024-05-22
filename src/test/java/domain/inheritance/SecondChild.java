package domain.inheritance;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SecondChild extends FirstChild {
    private Integer SecondChildInt;
    private Double SecondChildDouble;
}
