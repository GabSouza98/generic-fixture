package domain;

import lombok.*;

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

}
