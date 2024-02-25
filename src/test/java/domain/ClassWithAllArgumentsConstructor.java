package domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ClassWithAllArgumentsConstructor {

    private String name;
    private Integer number;
    private ComplexType complexType;
    private DummyWithArgsContructors dummyWithArgsContructors;

    public ClassWithAllArgumentsConstructor(String name, Integer number, ComplexType complexTypeeeeeee, DummyWithArgsContructors dummyWithArgsContructors) {
        this.name = name;
        this.number = number;
        this.complexType = complexTypeeeeeee;
        this.dummyWithArgsContructors = dummyWithArgsContructors;
    }


}
