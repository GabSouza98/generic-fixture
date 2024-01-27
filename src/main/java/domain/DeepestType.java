package domain;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Negative;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeepestType {
    private String deepest;

    @Future
    private LocalDateTime futureLocalDateTime;
}
