package domain;

import enums.CustomEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Dummy {

    private int primitiveInt;
    private double primitiveDouble;
    private ComplexType ComplexType;
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
    private List<UUID> uuidList;
    private CustomEnum CustomEnum;
    private Map<String, String> stringMap;
    private Dictionary<Integer, Integer> integerMap;
    private AbstractMap<Integer, String> mixedMap;
    private SortedMap<Integer, ComplexType> complexTypeMap;
    private Integer Integer;
    private BigDecimal bigDecimal;
    private ZonedDateTime zonedDateTime;
    private LocalDate localDate;
    private LocalTime localTime;
    private OffsetTime offsetTime;
    private Date date;
    private Instant instant;
    private ChronoLocalDate chronoLocalDate;
    private ChronoLocalDateTime chronoLocalDateTime;
    private ChronoZonedDateTime chronoZonedDateTime;
    private UUID uuid;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$")
    private String stringWithDateFormat;

    @Size(min = 3)
    private String minimumString;

    @Size(max = 6)
    private String maximumString;

    @Size(min = 4, max = 9)
    private String mediumString;

    @Min(2)
    private Integer minInteger;

    @Min(2)
    private Double minDouble;

    @Min(2)
    private long minLong;

    @Min(2)
    private BigDecimal minBigDecimal;

    @Min(2)
    private String minString;

    @Max(10)
    private Integer maxInteger;

    @Max(10)
    private double maxDouble;

    @Max(10)
    private Long maxLong;

    @Max(10)
    private String maxString;

    @Max(10)
    private BigDecimal maxBigDecimal;

    @Digits(integer = 5, fraction = 2)
    private BigDecimal digitsWithIntegerAndFractionBigDecimal;

    @Digits(integer = 5, fraction = 2)
    private String digitsWithIntegerAndFractionString;

    @Digits(integer = 5, fraction = 2)
    private Double digitsWithIntegerAndFractionDouble;

    @Digits(integer = 3, fraction = 2)
    private Long digitsWithIntegerAndFractionLong;

    @Digits(integer = 3, fraction = 1)
    private Integer digitsWithIntegerAndFractionInt;

    @Past
    private String pastDateString;

    @Past
    private OffsetDateTime pastOffsetDateTime;

    @Past
    private LocalDateTime pastLocalDateTime;

    @Past
    private Instant pastInstant;

    @Past
    private Date pastDate;

    @Past
    private ZonedDateTime pastZonedDateTime;

    @Past
    private LocalDate pastLocalDate;

    @Past
    private LocalTime pastLocalTime;

    @Past
    private OffsetTime pastOffsetTime;

    @PastOrPresent
    private String pastOrPresentDateString;

    @PastOrPresent
    private OffsetDateTime pastOrPresentOffsetDateTime;

    @PastOrPresent
    private LocalDateTime pastOrPresentLocalDateTime;

    @PastOrPresent
    private Instant pastOrPresentInstant;

    @PastOrPresent
    private Date pastOrPresentDate;

    @PastOrPresent
    private ZonedDateTime pastOrPresentZonedDateTime;

    @PastOrPresent
    private LocalDate pastOrPresentLocalDate;

    @PastOrPresent
    private LocalTime pastOrPresentLocalTime;

    @PastOrPresent
    private OffsetTime pastOrPresentOffsetTime;

    @Positive
    private int positiveInt;

    @Positive
    private Double positiveDouble;

    @Positive
    private long positiveLong;

    @Positive
    private String positiveString;

    @Positive
    private BigDecimal positiveBigDecimal;

    @PositiveOrZero
    private int positiveOrZeroInt;

    @PositiveOrZero
    private Double positiveOrZeroDouble;

    @PositiveOrZero
    private long positiveOrZeroLong;

    @PositiveOrZero
    private String positiveOrZeroString;

    @PositiveOrZero
    private BigDecimal positiveOrZeroBigDecimal;

    @Negative
    private int negativeInt;

    @Negative
    private double negativeDouble;

    @Negative
    private Long negativeLong;

    @Negative
    private String negativeString;

    @Negative
    private BigDecimal negativeBigDecimal;

    @NegativeOrZero
    private int negativeOrZeroInt;

    @NegativeOrZero
    private double negativeOrZeroDouble;

    @NegativeOrZero
    private Long negativeOrZeroLong;

    @NegativeOrZero
    private String negativeOrZeroString;

    @NegativeOrZero
    private BigDecimal negativeOrZeroBigDecimal;

    @Future
    private String futureDateString;

    @Future
    private OffsetDateTime futureOffsetDateTime;

    @Future
    private LocalDateTime futureLocalDateTime;

    @Future
    private Instant futureInstant;

    @Future
    private Date futureDate;

    @Future
    private ZonedDateTime futureZonedDateTime;

    @Future
    private LocalDate futureLocalDate;

    @Future
    private LocalTime futureLocalTime;

    @Future
    private OffsetTime futureOffsetTime;

    @FutureOrPresent
    private String futureOrPresentDateString;

    @FutureOrPresent
    private OffsetDateTime futureOrPresentOffsetDateTime;

    @FutureOrPresent
    private LocalDateTime futureOrPresentLocalDateTime;

    @FutureOrPresent
    private Instant futureOrPresentInstant;

    @FutureOrPresent
    private Date futureOrPresentDate;

    @FutureOrPresent
    private ZonedDateTime futureOrPresentZonedDateTime;

    @FutureOrPresent
    private LocalDate futureOrPresentLocalDate;

    @FutureOrPresent
    private LocalTime futureOrPresentLocalTime;

    @FutureOrPresent
    private OffsetTime futureOrPresentOffsetTime;

    @Email
    private String emailString;

    @DecimalMin(value = "3")
    private Double decimalMinDouble;

    @DecimalMin(value = "3")
    private String decimalMinString;

    @DecimalMin(value = "3")
    private BigDecimal decimalMinBigDecimal;

    @DecimalMax(value = "3")
    private Double decimalMaxDouble;

    @DecimalMax(value = "3")
    private String decimalMaxString;

    @DecimalMax(value = "3")
    private BigDecimal decimalMaxBigDecimal;

    @DecimalMax(value = "3")
    private Integer decimalMaxInteger;

    @DecimalMax(value = "3")
    private long decimalMaxLong;

}
