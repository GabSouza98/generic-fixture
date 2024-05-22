package domain.inheritance;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FirstChild extends Parent {
    private Double FirstChildDouble;
    private Boolean FirstChildBoolean;
    private LocalDateTime FirstChildLocalDateTime;
}
