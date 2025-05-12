package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.Negative;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ComplexType {

    private int primitiveInt;
    private double primitiveDouble;
    private String String;
    private Long Long;
    private Integer Integer;
    private Double Double;
    private Boolean Boolean;
    private DeepestType DeepestType;

    @Negative
    private String negativeString;

}
