package domain.constructors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplexBusinessLogicConstructor {
    private String att1;
    private String att2;
    private Integer att3;
    private Integer att4;
    private String att5;

    public ComplexBusinessLogicConstructor(String att1, String att2, Integer att3, Integer att4) {
        if (!att1.equals("1")) throw new RuntimeException("Invalid argument");
        if (!att2.equals("2")) throw new RuntimeException("Invalid argument");
        if (!att3.equals(3)) throw new RuntimeException("Invalid argument");
        if (!att4.equals(4)) throw new RuntimeException("Invalid argument");

        this.att1 = att1;
        this.att2 = att2;
        this.att3 = att3;
        this.att4 = att4;
    }
}
