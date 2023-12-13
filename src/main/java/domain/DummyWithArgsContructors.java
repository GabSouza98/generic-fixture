package domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class DummyWithArgsContructors {

    private String name;
    private int numero;
    private Long outroLong;
    private Double outroDouble;
    private ComplexType complexType;

    public DummyWithArgsContructors(String name, int numero, Long asdasd) {
        this.name = name;
        this.numero = numero;
        this.outroLong = asdasd;
    }

    public DummyWithArgsContructors(String name, int numero) {
        this.name = name;
        this.numero = numero;
    }


}
