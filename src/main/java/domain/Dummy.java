package domain;

import enums.CustomEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dummy {

    private static String stringEstatica = "minha string estatica";
    private final String constante = "CONSTANTE";

    //na raiz -> ""

    private int primitiveInt;
    private ComplexType ComplexType; //attributesPath = ComplexType
    private String String;
    private Long Long;
    private Double Double;
    private Boolean Boolean;
    private LocalDateTime LocalDateTime;
    private OffsetDateTime OffsetDateTime;
    private ArrayList<ComplexType> complexList;
    private LinkedList<Integer> integerList;
    private Set<Double> doubleList;
    private Deque<String> stringList;
    private SortedSet<Character> charList;
    private Queue<Boolean> booleanList;
    private CustomEnum CustomEnum;
    private Map<String, String> stringMap;
    private Dictionary<Integer, Integer> integerMap;
    private AbstractMap<Integer, String> mixedMap;
    private SortedMap<Integer, ComplexType> complexTypeMap;
    private Integer Integer;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$")
    private String stringWithDateFormat;

    @Size(min = 3)
    private String minimumString;

    @Size(max = 6)
    private String maximumString;

    @Size(min = 4, max = 9)
    private String mediumString;

    //TODO
    @Min(2)
    private Integer minInteger;

    @Max(10)
    private Integer maxInteger;

}
