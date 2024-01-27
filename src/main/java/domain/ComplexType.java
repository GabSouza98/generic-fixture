package domain;

import jakarta.validation.constraints.Negative;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComplexType {

    private String String;
    private Long Long;
    private Integer Integer;
    private Double Double;
    private Boolean Boolean;
    private DeepestType DeepestType;

    @Negative
    private String negativeString;

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
