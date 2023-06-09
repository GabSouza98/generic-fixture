package domain;

import enums.CustomEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dummy {

    private int primitiveInt;
    private String String;
    private Long Long;
    private Integer Integer;
    private Double Double;
    private Boolean Boolean;
    private LocalDateTime LocalDateTime;
    private OffsetDateTime OffsetDateTime;
    private ComplexType ComplexType;
    private List<ComplexType> list;
    private CustomEnum CustomEnum;

    @Override
    public String toString() {
        return "Dummy {\n" +
                "    primetiveInt=" + primitiveInt +  "\n" +
                "    String=" + String + "\n" +
                "    Long=" + Long + "\n" +
                "    Integer=" + Integer +"\n" +
                "    Double=" + Double +"\n" +
                "    Boolean=" + Boolean +"\n" +
                "    LocalDateTime=" + LocalDateTime +"\n" +
                "    OffsetDateTime=" + OffsetDateTime +"\n" +
                "    ComplexType=" + ComplexType +"\n" +
                "    CustomEnum=" + CustomEnum +"\n" +
                "    List=" + list + "\n" +
                "    }";
    }
}
