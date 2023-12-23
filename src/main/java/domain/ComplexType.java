package domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplexType {

    private int primitiveInt;
    private double primitiveDouble;
    private String String;
    private Long Long;
    private Integer Integer;
    private Double Double;
    private Boolean Boolean;
    private DeepestType DeepestType;

    @Override
    public java.lang.String toString() {
        return "ComplexType {\n" +
                "        String='" + String + '\'' + "\n" +
                "        Long=" + Long +"\n" +
                "        Integer=" + Integer +"\n" +
                "        Double=" + Double +"\n" +
                "        Boolean=" + Boolean +"\n" +
                "        }";
    }
}
