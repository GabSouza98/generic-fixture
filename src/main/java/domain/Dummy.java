package domain;

import enums.CustomEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dummy {

//    private int primitiveInt;
//    private String String;
//    private Long Long;
//    private Integer Integer;
//    private Double Double;
//    private Boolean Boolean;
//    private LocalDateTime LocalDateTime;
//    private OffsetDateTime OffsetDateTime;
//    private ComplexType ComplexType;
//    private List<Integer> list;
//    private CustomEnum CustomEnum;
    private Map<Long, Long> customMap;

//    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$")
//    private String stringWithDateFormat;
//
//    @Size(min = 3)
//    private String minimumString;
//
//    @Size(max = 6)
//    private String maximumString;
//
//    @Size(min = 4, max = 9)
//    private String mediumString;

    //TODO
//    @Min(2)
//    private Integer minInteger;
//
//    @Max(10)
//    private Integer maxInteger;

}
