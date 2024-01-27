import domain.ComplexType;
import domain.Dummy;
import domain.DummyWithArgsContructors;
import generic.fixture.GenericFixture;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GenericFixtureTest {

    public static final String NAME = "Da Silva";
    public static final Integer NUMBER_TEN = 10;
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_.]{1,10}@email\\.com$";
    public static final String REGEX_THREE_DIGTS_AFTER_POINT = "\\d+\\.\\d{3}";
    public static final String REGEX_THREE_DIGITS_BEFORE_POINT = "\\d{3}\\.\\d+";
    public static final String REGEX_SIZE_THREE = "\\d{3}";
    public static final String FIVE_TWO_DECIMAL_REGEX = "\\d{5}\\.\\d{2}";
    HashMap<String, Object> customFields = new HashMap<>();

    @Test
    void shouldValidateAllFields() {
        Dummy dummy = GenericFixture.generate(Dummy.class);
        assertNotNull(dummy.getBigDecimal());
        assertTrue(dummy.getPrimitiveInt() != 0, "Erro para o tipo int");
        assertTrue(dummy.getPrimitiveDouble() != 0, "Erro para o tipo int");
        assertNotNull(dummy.getString(), "Erro para o tipo String");
        assertNotNull(dummy.getInteger(), "Erro para o tipo Integer");
        assertNotNull(dummy.getDouble(), "Erro para o tipo Double");
        assertNotNull(dummy.getLocalDateTime(), "Erro para o tipo LocalDateTime");
        assertNotNull(dummy.getOffsetDateTime(), "Erro para o tipo OffsetDateTime");
        assertNotNull(dummy.getComplexType(), "Erro para o tipo ComplexType");
        assertNotNull(dummy.getComplexList(), "Erro para o tipo complexList");
        assertNotNull(dummy.getComplexList().get(0), "Erro para inserir registro na complexList");
        assertNotNull(dummy.getIntegerList(), "Erro para o tipo integerList");
        assertNotNull(dummy.getDoubleList(), "Erro para o tipo doubleList");
        assertNotNull(dummy.getStringList(), "Erro para o tipo stringList");
        assertNotNull(dummy.getCharList(), "Erro para o tipo charList");
        assertNotNull(dummy.getBooleanList(), "Erro para o tipo booleanList");
        assertNotNull(dummy.getCustomEnum(), "Erro para o tipo CustomEnum");
        assertNotNull(dummy.getStringMap(), "Erro para o tipo stringMap");
        assertNotNull(dummy.getIntegerMap(), "Erro para o tipo integerMap");
        assertNotNull(dummy.getMixedMap(), "Erro para o tipo mixedMap");
        assertNotNull(dummy.getComplexTypeMap(), "Erro para o tipo complexMap");
        assertEquals(1, dummy.getStringMap().size(), "Erro para inserir no CustomMap");
    }

    @Test
    void shouldTestFieldWithAnnotations() {
        Dummy dummy = GenericFixture.generate(Dummy.class);
        assertTrue(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", dummy.getStringWithDateFormat()), "Erro para formatar no pattern");
        assertTrue(dummy.getMinimumString().length() >= 3, "Erro para a anotação Size(min)");
        assertTrue(dummy.getMaximumString().length() <= 6, "Erro para a anotação Size(max)");
        assertTrue(dummy.getMediumString().length() >= 4, "Erro para a anotação Size(min, max)");
        assertTrue(dummy.getMediumString().length() <= 9, "Erro para a anotação Size(min, max)");

        assertTrue(dummy.getPastLocalDateTime().isBefore(LocalDateTime.now()), format("Erro para a anotação Past na classe LocalDateTime, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastLocalDateTime()));
        assertTrue(dummy.getPastInstant().isBefore(Instant.now()), format("Erro para a anotação Past na classe Instant, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastInstant()));
        assertTrue(dummy.getPastOffsetDateTime().isBefore(OffsetDateTime.now()), format("Erro para a anotação Past na classe OffSetDateTime, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastOffsetDateTime()));
        assertTrue(dummy.getPastZonedDateTime().isBefore(ZonedDateTime.now()), format("Erro para a anotação Past na classe ZonedDateTime, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastZonedDateTime()));
        assertTrue(dummy.getPastLocalDate().isBefore(LocalDate.now()), format("Erro para a anotação Past na classe LocalDate, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastLocalDate()));
        assertTrue(dummy.getPastLocalTime().isBefore(LocalTime.now()), format("Erro para a anotação Past na classe LocalTime, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastLocalTime()));
        assertTrue(dummy.getPastOffsetTime().isBefore(OffsetTime.now()), format("Erro para a anotação Past na classe OffsetTime, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastOffsetTime()));
        assertNotNull(dummy.getPastDate(), "Erro para a anotação Past na classe String");
        assertTrue(dummy.getPastDate().before(new Date()), "Erro para a anotação Past na classe date");

        assertTrue(dummy.getPastOrPresentLocalDateTime().isBefore(LocalDateTime.now()), format("Erro para a anotação PastOrPresent na classe LocalDateTime, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastOrPresentLocalDateTime()));
        assertTrue(dummy.getPastOrPresentInstant().isBefore(Instant.now().minus(1, ChronoUnit.MINUTES)), format("Erro para a anotação PastOrPresent na classe Instant, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastOrPresentInstant()));
        assertTrue(dummy.getPastOrPresentZonedDateTime().isBefore(ZonedDateTime.now()), format("Erro para a anotação PastOrPresent na classe ZonedDateTime, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastOrPresentZonedDateTime()));
        assertTrue(dummy.getPastOrPresentLocalDate().isBefore(LocalDate.now()), format("Erro para a anotação PastOrPresent na classe LocalDate, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastOrPresentLocalDate()));
        assertTrue(dummy.getPastOrPresentLocalTime().isBefore(LocalTime.now()), format("Erro para a anotação PastOrPresent na classe LocalTime, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastOrPresentLocalTime()));
        assertTrue(dummy.getPastOrPresentOffsetTime().isBefore(OffsetTime.now()), format("Erro para a anotação PastOrPresent na classe OffsetTime, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastOrPresentOffsetTime()));
        assertTrue(dummy.getPastOrPresentOffsetDateTime().isBefore(OffsetDateTime.now()), format("Erro para a anotação PastOrPresent na classe OffSetDateTime", dummy.getPastOrPresentOffsetDateTime()));
        assertNotNull(dummy.getPastOrPresentDate(), "Erro para a anotação PastOrPresent na classe String");
        assertTrue(dummy.getPastOrPresentDate().before(new Date()), format("Erro para a anotação PastOrPresent na classe date, valor gerado: %s, o mesmo precisa ser menor que a data atual", dummy.getPastOrPresentDate()));

        assertNotNull(dummy.getDate(), "Erro para a classe date");
        assertNotNull(dummy.getZonedDateTime(), "Erro para a classe ZonedDateTime");
        assertNotNull(dummy.getLocalDate(), "Erro para a classe LocalDate");
        assertNotNull(dummy.getLocalTime(), "Erro para a classe LocalTime");
        assertNotNull(dummy.getOffsetTime(), "Erro para a classe OffsetTime");


        assertTrue(dummy.getPositiveInt() > 0);
        assertTrue(dummy.getPositiveDouble() > 0);
        assertTrue(dummy.getPositiveLong() > 0);
        assertTrue(dummy.getPositiveBigDecimal().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(Integer.parseInt(dummy.getPositiveString()) > 0);

        assertTrue(dummy.getPositiveOrZeroInt() > 0);
        assertTrue(dummy.getPositiveOrZeroDouble() > 0);
        assertTrue(dummy.getPositiveOrZeroLong() > 0);
        assertTrue(dummy.getPositiveOrZeroBigDecimal().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(Integer.parseInt(dummy.getPositiveOrZeroString()) > 0);

        assertTrue(dummy.getNegativeInt() < 0);
        assertTrue(dummy.getNegativeDouble() < 0);
        assertTrue(dummy.getNegativeLong() < 0);
        assertTrue(Integer.parseInt(dummy.getNegativeString()) < 0);
        assertTrue(dummy.getNegativeBigDecimal().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(Integer.parseInt(dummy.getComplexType().getNegativeString()) < 0);

        assertTrue(dummy.getNegativeOrZeroInt() < 0);
        assertTrue(dummy.getNegativeOrZeroDouble() < 0);
        assertTrue(dummy.getNegativeOrZeroLong() < 0);
        assertTrue(Integer.parseInt(dummy.getNegativeOrZeroString()) < 0);
        assertTrue(dummy.getNegativeOrZeroBigDecimal().compareTo(BigDecimal.ZERO) < 0);

        assertTrue(dummy.getFutureLocalDateTime().isAfter(LocalDateTime.now()), format("Erro para a anotação Future na classe LocalDateTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureLocalDateTime()));
        assertTrue(dummy.getFutureInstant().isAfter(Instant.now()), format("Erro para a anotação Future na classe Instant, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureInstant()));
        assertTrue(dummy.getFutureOffsetDateTime().isAfter(OffsetDateTime.now()), format("Erro para a anotação Future na classe OffSetDateTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOffsetDateTime()));
        assertTrue(dummy.getFutureZonedDateTime().isAfter(ZonedDateTime.now()), format("Erro para a anotação Future na classe ZonedDateTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureZonedDateTime()));
        assertTrue(dummy.getFutureLocalDate().isAfter(LocalDate.now()), format("Erro para a anotação Future na classe LocalDate, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureLocalDate()));
        assertTrue(dummy.getFutureLocalTime().isAfter(LocalTime.now()), format("Erro para a anotação Future na classe LocalTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureLocalTime()));
        assertTrue(dummy.getFutureOffsetTime().isAfter(OffsetTime.now()), format("Erro para a anotação Future na classe OffsetTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOffsetTime()));
        assertTrue(dummy.getFutureDate().after(new Date()), format("Erro para a anotação Future na classe date, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureDate()));
        assertNotNull(dummy.getFutureDateString(), "Erro para a anotação Future na classe String");
        assertTrue(dummy.getComplexType().getDeepestType().getFutureLocalDateTime().isAfter(LocalDateTime.now()), format("Erro para a anotação Future na classe ComplexType.DeepestType.LocalDateTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getComplexType().getDeepestType().getFutureLocalDateTime()));

        assertTrue(dummy.getFutureOrPresentLocalDateTime().isAfter(LocalDateTime.now()), format("Erro para a anotação FutureOrPresent na classe LocalDateTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOrPresentLocalDateTime()));
        assertTrue(dummy.getFutureOrPresentInstant().isAfter(Instant.now()), format("Erro para a anotação FutureOrPresent na classe Instant, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOrPresentInstant()));
        assertTrue(dummy.getFutureOrPresentOffsetDateTime().isAfter(OffsetDateTime.now()), format("Erro para a anotação FutureOrPresent na classe OffSetDateTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOrPresentOffsetDateTime()));
        assertTrue(dummy.getFutureOrPresentZonedDateTime().isAfter(ZonedDateTime.now()), format("Erro para a anotação FutureOrPresent na classe ZonedDateTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOrPresentZonedDateTime()));
        assertTrue(dummy.getFutureOrPresentLocalDate().isAfter(LocalDate.now()), format("Erro para a anotação FutureOrPresent na classe LocalDate, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOrPresentLocalDate()));
        assertTrue(dummy.getFutureOrPresentLocalTime().isAfter(LocalTime.now()), format("Erro para a anotação FutureOrPresent na classe LocalTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOrPresentLocalTime()));
        assertTrue(dummy.getFutureOrPresentOffsetTime().isAfter(OffsetTime.now()), format("Erro para a anotação FutureOrPresent na classe OffsetTime, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOrPresentOffsetTime()));
        assertTrue(dummy.getFutureOrPresentDate().after(new Date()), format("Erro para a anotação FutureOrPresent na classe date, valor gerado: %s, o mesmo precisa ser maior que a data atual", dummy.getFutureOrPresentDate()));
        assertNotNull(dummy.getFutureOrPresentDateString(), "Erro para a anotação FutureOrPresent na classe String");

        assertTrue(dummy.getEmailString().matches(EMAIL_REGEX));
        assertNotNull(dummy.getDigitsWithIntegerAndFractionBigDecimal());

        assertTrue(dummy.getDecimalMinDouble().toString().matches(REGEX_THREE_DIGTS_AFTER_POINT));
        assertTrue(dummy.getDecimalMinString().matches(REGEX_THREE_DIGTS_AFTER_POINT));
        assertTrue(dummy.getDecimalMinBigDecimal().toString().matches(REGEX_THREE_DIGTS_AFTER_POINT));

        assertTrue(dummy.getDecimalMaxDouble().toString().matches(REGEX_THREE_DIGITS_BEFORE_POINT));
        assertTrue(dummy.getDecimalMaxString().matches(REGEX_SIZE_THREE));
        assertTrue(dummy.getDecimalMaxBigDecimal().toString().matches(REGEX_SIZE_THREE));
        assertTrue(dummy.getDecimalMaxInteger().toString().matches(REGEX_SIZE_THREE));
        assertTrue(String.valueOf(dummy.getDecimalMaxLong()).matches(REGEX_SIZE_THREE));

        assertNotNull(dummy.getBigDecimal());
        assertTrue(dummy.getDigitsWithIntegerAndFractionBigDecimal().toString().matches(FIVE_TWO_DECIMAL_REGEX));
        assertTrue(dummy.getDigitsWithIntegerAndFractionString().matches(FIVE_TWO_DECIMAL_REGEX));
        assertTrue(dummy.getDigitsWithIntegerAndFractionDouble().toString().matches(FIVE_TWO_DECIMAL_REGEX));
        assertTrue(dummy.getDigitsWithIntegerAndFractionLong().toString().matches(REGEX_SIZE_THREE));
        assertTrue(dummy.getDigitsWithIntegerAndFractionInt().toString().matches(REGEX_SIZE_THREE));

    }
    @Test
    void shouldIgnore_String_Field() {
        customFields.put("String", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_ComplexType_Field() {
        customFields.put("ComplexType", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexType());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_ComplexType_String_Field() {
        customFields.put("ComplexType.String", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNotNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_complexList_Field() {
        customFields.put("complexList", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexList());
        assertNotNull(dummyWithIgnoredFields.getComplexType());
    }

    @Test
    void shouldIgnore_complexList_String_Field() {
        customFields.put("complexList.String", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
        assertNotNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getString());
    }

    @Test
    void shouldIgnore_ComplexType_And_String_Field() {
        customFields.put("ComplexType", null);
        customFields.put("String", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexType());
        assertNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_ComplexTypeString_And_String_Field() {
        customFields.put("ComplexType.String", null);
        customFields.put("String", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_complexListString_And_String_Field() {
        customFields.put("complexList.String", null);
        customFields.put("String", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
        assertNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getString());
    }

    @Test
    void shouldIgnore_ComplexTypeString_And_complexListString_And_String_Field() {
        customFields.put("ComplexType.String", null);
        customFields.put("String", null);
        customFields.put("complexList.String", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNull(dummyWithIgnoredFields.getString());
        assertNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_ComplexTypeString_And_complexListString_Field() {
        customFields.put("ComplexType.String", null);
        customFields.put("complexList.String", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
        assertNotNull(dummyWithIgnoredFields.getString());
    }

    @Test
    void shouldIgnore_Integer_Field() {
        customFields.put("Integer", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getInteger());
        assertNotNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getInteger());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getInteger());
        assertNotNull(dummyWithIgnoredFields.getIntegerList().get(0));
    }

    @Test
    void shouldIgnore_ComplexTypeInteger_Field() {
        customFields.put("ComplexType.Integer", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexType().getInteger());
        assertNotNull(dummyWithIgnoredFields.getInteger());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getInteger());
        assertNotNull(dummyWithIgnoredFields.getIntegerList().get(0));
    }

    @Test
    void shouldIgnore_ComplexTypeDeepestTypeDeepest_Field() {
        customFields.put("ComplexType.DeepestType.deepest", null);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertNull(dummyWithIgnoredFields.getComplexType().getDeepestType().getDeepest());
    }

    //TODO ABAIXO
    @Test
    void shouldSet_ComplexType_Field() {
        ComplexType complexType = GenericFixture.generate(ComplexType.class);
        customFields.put("ComplexType", complexType);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(complexType.getDeepestType(), dummyWithIgnoredFields.getComplexType().getDeepestType());
        assertEquals(complexType.getString(), dummyWithIgnoredFields.getComplexType().getString());
        assertEquals(complexType.getDouble(), dummyWithIgnoredFields.getComplexType().getDouble());
        assertEquals(complexType.getLong(), dummyWithIgnoredFields.getComplexType().getLong());
        assertEquals(complexType.getInteger(), dummyWithIgnoredFields.getComplexType().getInteger());
        assertEquals(complexType.getBoolean(), dummyWithIgnoredFields.getComplexType().getBoolean());
        assertEquals(complexType.getPrimitiveInt(), dummyWithIgnoredFields.getComplexType().getPrimitiveInt());
        assertEquals(complexType.getPrimitiveDouble(), dummyWithIgnoredFields.getComplexType().getPrimitiveDouble());
        assertEquals(complexType.getDeepestType().getDeepest(), dummyWithIgnoredFields.getComplexType().getDeepestType().getDeepest());

        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldSet_ComplexType_String_Field() {
        customFields.put("ComplexType.String", NAME);
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(NAME, dummyWithIgnoredFields.getComplexType().getString());
        assertNotNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldSet_complexList_Field() {
        customFields.put("complexList", new ArrayList<ComplexType>());
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertTrue(dummyWithIgnoredFields.getComplexList().isEmpty());
        assertNotNull(dummyWithIgnoredFields.getComplexType());
    }

    @Test
    void shouldSet_complexList_String_Field() {
        customFields.put("complexList.String", NAME);

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(NAME, dummyWithIgnoredFields.getComplexList().get(0).getString());
        assertNotNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getString());
    }

    @Test
    void shouldSet_ComplexType_And_String_Field() {
        ComplexType complexType = GenericFixture.generate(ComplexType.class);
        customFields.put("ComplexType", complexType);
        customFields.put("String", NAME);

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(complexType.getDeepestType(), dummyWithIgnoredFields.getComplexType().getDeepestType());
        assertEquals(complexType.getString(), dummyWithIgnoredFields.getComplexType().getString());
        assertEquals(complexType.getDouble(), dummyWithIgnoredFields.getComplexType().getDouble());
        assertEquals(complexType.getLong(), dummyWithIgnoredFields.getComplexType().getLong());
        assertEquals(complexType.getInteger(), dummyWithIgnoredFields.getComplexType().getInteger());
        assertEquals(complexType.getBoolean(), dummyWithIgnoredFields.getComplexType().getBoolean());
        assertEquals(complexType.getDeepestType().getDeepest(), dummyWithIgnoredFields.getComplexType().getDeepestType().getDeepest());
        assertEquals(NAME, dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldSet_ComplexTypeString_And_String_Field() {
        customFields.put("ComplexType.String", NAME);
        customFields.put("String", NAME);

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(NAME, dummyWithIgnoredFields.getComplexType().getString());
        assertEquals(NAME, dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldSet_complexListString_And_String_Field() {
        customFields.put("complexList.String", NAME);
        customFields.put("String", NAME);

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(NAME, dummyWithIgnoredFields.getComplexList().get(0).getString());
        assertEquals(NAME, dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getString());
    }

    @Test
    void shouldSet_ComplexTypeString_And_complexListString_And_String_Field() {
        customFields.put("ComplexType.String", NAME);
        customFields.put("String", NAME);
        customFields.put("complexList.String", NAME);

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(NAME, dummyWithIgnoredFields.getComplexType().getString());
        assertEquals(NAME, dummyWithIgnoredFields.getString());
        assertEquals(NAME, dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldSet_ComplexTypeString_And_complexListString_Field() {
        customFields.put("ComplexType.String", NAME);
        customFields.put("complexList.String", NAME);

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(NAME, dummyWithIgnoredFields.getComplexType().getString());
        assertEquals(NAME, dummyWithIgnoredFields.getComplexList().get(0).getString());
        assertNotNull(dummyWithIgnoredFields.getString());
    }

    @Test
    void shouldSet_Integer_Field() {
        customFields.put("Integer", NUMBER_TEN);

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(NUMBER_TEN, dummyWithIgnoredFields.getInteger());
        assertNotNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getInteger());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getInteger());
        assertNotNull(dummyWithIgnoredFields.getIntegerList().get(0));
    }

    @Test
    void shouldSet_ComplexTypeInteger_Field() {
        customFields.put("ComplexType.Integer", NUMBER_TEN);

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(NUMBER_TEN, dummyWithIgnoredFields.getComplexType().getInteger());
        assertNotNull(dummyWithIgnoredFields.getInteger());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getInteger());
        assertNotNull(dummyWithIgnoredFields.getIntegerList().get(0));
    }

    @Test
    void shouldSet_ComplexTypeDeepestTypeDeepest_Field() {
        customFields.put("ComplexType.DeepestType.deepest", NAME);

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, customFields);

        assertEquals(NAME, dummyWithIgnoredFields.getComplexType().getDeepestType().getDeepest());
    }

    @Test
    void shouldCreateInstanceWithoutNoArgsConstructor() {

        DummyWithArgsContructors dummyWithArgsContructors = GenericFixture.generate(DummyWithArgsContructors.class);

        assertNotNull(dummyWithArgsContructors.getName());
        assertNotNull(dummyWithArgsContructors.getNumber());
        assertNotNull(dummyWithArgsContructors.getAnyLong());
        assertNotNull(dummyWithArgsContructors.getAnyDouble());
        assertNotNull(dummyWithArgsContructors.getComplexType().getBoolean());
        assertNotNull(dummyWithArgsContructors.getComplexType().getString());
        assertNotNull(dummyWithArgsContructors.getComplexType().getLong());
        assertNotNull(dummyWithArgsContructors.getComplexType().getInteger());
        assertNotNull(dummyWithArgsContructors.getComplexType().getDouble());
        assertNotNull(dummyWithArgsContructors.getComplexType().getDeepestType().getDeepest());
    }

    @Test
    void shouldCreateInstanceWithoutNoArgsConstructorAndCustomValues() {

        customFields.put("name", NAME);
        customFields.put("anyLong", null);

        DummyWithArgsContructors dummyWithArgsContructors = GenericFixture.generate(DummyWithArgsContructors.class, customFields);

        assertEquals(NAME, dummyWithArgsContructors.getName());
        assertNull(dummyWithArgsContructors.getAnyLong());
        assertNotNull(dummyWithArgsContructors.getNumber());
        assertNotNull(dummyWithArgsContructors.getAnyDouble());
        assertNotNull(dummyWithArgsContructors.getComplexType().getBoolean());
        assertNotNull(dummyWithArgsContructors.getComplexType().getString());
        assertNotNull(dummyWithArgsContructors.getComplexType().getLong());
        assertNotNull(dummyWithArgsContructors.getComplexType().getInteger());
        assertNotNull(dummyWithArgsContructors.getComplexType().getDouble());
        assertNotNull(dummyWithArgsContructors.getComplexType().getDeepestType().getDeepest());

    }
}