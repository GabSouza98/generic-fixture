package domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class DummyWithArgsContructors {

    private String name;
    private int number;
    private Long anyLong;
    private Double anyDouble;
    private ComplexType complexType;

    public DummyWithArgsContructors(String name, int number, Long anyLong) {
        this.name = name;
        this.number = number;
        this.anyLong = anyLong;
    }

    public DummyWithArgsContructors(String name, int number) {
        this.name = name;
        this.number = number;
    }

}
