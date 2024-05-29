package domain.constructors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExplicitDefaultConstructor {
    ExplicitDefaultConstructor() {}
    private String fieldB;
}
