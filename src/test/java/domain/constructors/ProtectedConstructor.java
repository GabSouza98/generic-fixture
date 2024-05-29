package domain.constructors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProtectedConstructor {
    protected ProtectedConstructor() {}
    private String fieldA;
}