package domain;

import enums.CustomEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DomainArray {

    private Double[] arrayDouble;
    private double[] arrayDoublePrimitive;
    private Long[] arrayLong;
    private long[] arrayLongPrimitive;
    private Integer[] arrayInteger;
    private int[] arrayIntegerPrimitive;
    private Boolean[] arrayBoolean;
    private boolean[] arrayBooleanPrimitive;
    private Character[] arrayCharacter;
    private char[] arrayCharacterPrimitive;
    private LocalDateTime[] arrayLocalDateTime;
    private OffsetDateTime[] arrayOffsetDateTime;
    private CustomEnum[] arrayCustomEnum;
    private ComplexType[] arrayComplexType;
    private LocalDate[] arrayLocalDate;
    private LocalTime[] arrayLocalTime;
    private OffsetTime[] arrayOffsetTime;
    private Date[] arrayDate;
    private BigDecimal[] arrayBigDecimal;
    private Map<Integer, Integer>[] arrayMapIntegerInteger;
    private Map<String, Integer>[] arrayMapStringInteger;
    private Map<String, CustomEnum>[] arrayMapStringCustomEnum;
    private Map<CustomEnum, String>[] arrayMapCustomEnumString;
    private Map<Integer, LocalDateTime>[] arrayMapIntegerLocalDateTime;
    private Map<Character, BigDecimal>[] arrayMapCharacterBigDecimal;
    private Map<Integer, ComplexType>[] arrayMapIntegerComplexyType;
    private Map<String, ComplexType>[] arrayMapStringComplexyType;
    private Map<ComplexType, String>[] arrayMapComplexyTypeString;
    private HashMap<Integer, Integer>[] arrayHashMapIntegerInteger;
    private HashMap<String, Integer>[] arrayHashMapStringInteger;
    private HashMap<String, CustomEnum>[] arrayHashMapStringCustomEnum;
    private HashMap<CustomEnum, String>[] arrayHashMapCustomEnumString;
    private HashMap<Integer, LocalDateTime>[] arrayHashMapIntegerLocalDateTime;
    private HashMap<Character, BigDecimal>[] arrayHashMapCharacterBigDecimal;
    private HashMap<Integer, ComplexType>[] arrayHashMapIntegerComplexyType;
    private HashMap<String, ComplexType>[] arrayHashMapStringComplexyType;;
    private Dictionary<Integer, Integer>[] arrayDictionaryIntegerInteger;
    private Dictionary<String, Integer>[] arrayDictionaryStringInteger;
    private Dictionary<String, CustomEnum>[] arrayDictionaryStringCustomEnum;
    private Dictionary<CustomEnum, String>[] arrayDictionaryCustomEnumString;
    private Dictionary<Integer, LocalDateTime>[] arrayDictionaryIntegerLocalDateTime;
    private Dictionary<Character, BigDecimal>[] arrayDictionaryCharacterBigDecimal;
    private Dictionary<Integer, ComplexType>[] arrayDictionaryIntegerComplexyType;
    private Dictionary<String, ComplexType>[] arrayDictionaryStringComplexyType;
    private TreeMap<Integer, String>[] arrayTreeMapIntegerString;
}
