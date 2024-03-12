package generic.fixture;

import domain.*;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        assertTrue(dummy.getPrimitiveInt() != 0, "Error for primitive int type attribute");
        assertTrue(dummy.getPrimitiveDouble() != 0, "Error for primitive double type attribute");
        assertNotNull(dummy.getString(), "Error for String type attribute");
        assertNotNull(dummy.getInteger(), "Error for Integer type attribute");
        assertNotNull(dummy.getDouble(), "Error for Double type attribute");
        assertNotNull(dummy.getLocalDateTime(), "Error for LocalDateTime type attribute");
        assertNotNull(dummy.getOffsetDateTime(), "Error for OffsetDateTime type attribute");
        assertNotNull(dummy.getDate(), "Error for Date type attribute");
        assertNotNull(dummy.getZonedDateTime(), "Error for ZonedDateTime type attribute");
        assertNotNull(dummy.getLocalDate(), "Error for LocalDate type attribute");
        assertNotNull(dummy.getLocalTime(), "Error for LocalTime type attribute");
        assertNotNull(dummy.getOffsetTime(), "Error for OffsetTime type attribute");
        assertNotNull(dummy.getComplexType(), "Error for ComplexType type attribute");
        assertNotNull(dummy.getComplexList(), "Error for ComplexList type attribute");
        assertNotNull(dummy.getComplexList().get(0), "Error to insert in ComplexList");
        assertNotNull(dummy.getIntegerList(), "Error for Integer list type attribute");
        assertNotNull(dummy.getDoubleList(), "Error for Double list type attribute");
        assertNotNull(dummy.getStringList(), "Error for String list type attribute");
        assertNotNull(dummy.getCharList(), "Error for Char list type attribute");
        assertNotNull(dummy.getBooleanList(), "Error for Boolean list type attribute");
        assertNotNull(dummy.getUuidList(), "Error for UUID list type attribute");
        assertNotNull(dummy.getCustomEnum(), "Error for CustomEnum type attribute");
        assertNotNull(dummy.getStringMap(), "Error for String map type attribute");
        assertNotNull(dummy.getIntegerMap(), "Error for Integer map type attribute");
        assertNotNull(dummy.getMixedMap(), "Error for MixedMap type attribute");
        assertNotNull(dummy.getComplexTypeMap(), "Error for ComplexType map type attribute");
        assertNotNull(dummy.getUuid(), "Error to insert in UUID");
        assertNotNull(dummy.getInstant(), "Error to insert in Instant");
        assertNotNull(dummy.getChronoLocalDate(), "Error to insert in ChronoLocalDate");
        assertNotNull(dummy.getChronoLocalDateTime(), "Error to insert in ChronoLocalDateTime");
        assertNotNull(dummy.getChronoZonedDateTime(), "Error to insert in ChronoZonedDateTime");
        assertEquals(1, dummy.getStringMap().size(), "Error to insert in String map");

    }

    @Test
    void shouldTestFieldWithAnnotations() {
        Dummy dummy = GenericFixture.generate(Dummy.class);
        assertTrue(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", dummy.getStringWithDateFormat()), "Error for Pattern annotation");
        assertTrue(dummy.getMinimumString().length() >= 3, "Error for Size(min) annotation");
        assertTrue(dummy.getMaximumString().length() <= 6, "Error for Size(max) annotation");
        assertTrue(dummy.getMediumString().length() >= 4, "Error for Size(min, max) annotation");
        assertTrue(dummy.getMediumString().length() <= 9, "Error for Size(min, max) annotation");

        assertTrue(dummy.getPastLocalDateTime().isBefore(LocalDateTime.now()), format("Error for Past annotation in attribute LocalDateTime, generated value: %s, the value needs to be less than actual date", dummy.getPastLocalDateTime()));
        assertTrue(dummy.getPastInstant().isBefore(Instant.now()), format("Error for Past annotation in attribute Instant, generated value: %s, the value needs to be less than actual date", dummy.getPastInstant()));
        assertTrue(dummy.getPastOffsetDateTime().isBefore(OffsetDateTime.now()), format("Error for Past annotation in attribute OffsetDateTime, generated value: %s, the value needs to be less than actual date", dummy.getPastOffsetDateTime()));
        assertTrue(dummy.getPastZonedDateTime().isBefore(ZonedDateTime.now()), format("Error for Past annotation in attribute ZonedDateTime, generated value: %s, the value needs to be less than actual date", dummy.getPastZonedDateTime()));
        assertTrue(dummy.getPastLocalDate().isBefore(LocalDate.now()), format("Error for Past annotation in attribute LocalDate, generated value: %s, the value needs to be less than actual date", dummy.getPastLocalDate()));
        assertTrue(dummy.getPastLocalTime().isBefore(LocalTime.now()), format("Error for Past annotation in attribute LocalTime, generated value: %s, the value needs to be less than actual date", dummy.getPastLocalTime()));
        assertTrue(dummy.getPastOffsetTime().isBefore(OffsetTime.now()), format("Error for Past annotation in attribute OffsetTime, generated value: %s, the value needs to be less than actual date", dummy.getPastOffsetTime()));
        assertTrue(dummy.getPastDate().before(new Date()), format("Error for Past annotation in attribute Date, generated value: %s, the value needs to be less than actual date", dummy.getPastDate()));

        assertTrue(dummy.getPastOrPresentLocalDateTime().isBefore(LocalDateTime.now()), format("Error for PastOrPresent annotation in attribute LocalDateTime, generated value: %s, the value needs to be less than actual date", dummy.getPastOrPresentLocalDateTime()));
        assertTrue(dummy.getPastOrPresentInstant().isBefore(Instant.now().minus(1, ChronoUnit.MINUTES)), format("Error for PastOrPresent annotation in attribute Instant, generated value: %s, the value needs to be less than actual date", dummy.getPastOrPresentInstant()));
        assertTrue(dummy.getPastOrPresentZonedDateTime().isBefore(ZonedDateTime.now()), format("Error for PastOrPresent annotation in attribute ZonedDateTime, generated value: %s, the value needs to be less than actual date", dummy.getPastOrPresentZonedDateTime()));
        assertTrue(dummy.getPastOrPresentLocalDate().isBefore(LocalDate.now()), format("Error for PastOrPresent annotation in attribute LocalDate, generated value: %s, the value needs to be less than actual date", dummy.getPastOrPresentLocalDate()));
        assertTrue(dummy.getPastOrPresentLocalTime().isBefore(LocalTime.now()), format("Error for PastOrPresent annotation in attribute LocalTime, generated value: %s, the value needs to be less than actual date", dummy.getPastOrPresentLocalTime()));
        assertTrue(dummy.getPastOrPresentOffsetTime().isBefore(OffsetTime.now()), format("Error for PastOrPresent annotation in attribute OffsetTime, generated value: %s, the value needs to be less than actual date", dummy.getPastOrPresentOffsetTime()));
        assertTrue(dummy.getPastOrPresentOffsetDateTime().isBefore(OffsetDateTime.now()), format("Error for PastOrPresent annotation in attribute OffsetDateTime, generated value: %s, the value needs to be less than actual date", dummy.getPastOrPresentOffsetDateTime()));
        assertTrue(dummy.getPastOrPresentDate().before(new Date()), format("Error for PastOrPresent annotation in attribute Date, generated value: %s, the value needs to be less than actual date", dummy.getPastOrPresentDate()));

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

        assertTrue(dummy.getFutureLocalDateTime().isAfter(LocalDateTime.now()), format("Error for Future annotation in attribute LocalDateTime, generated value: %s, the value needs to be grander than actual date", dummy.getFutureLocalDateTime()));
        assertTrue(dummy.getFutureInstant().isAfter(Instant.now()), format("Error for Future annotation in attribute Instant, generated value: %s, the value needs to be grander than actual date", dummy.getFutureInstant()));
        assertTrue(dummy.getFutureOffsetDateTime().isAfter(OffsetDateTime.now()), format("Error for Future annotation in attribute OffsetDateTime, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOffsetDateTime()));
        assertTrue(dummy.getFutureZonedDateTime().isAfter(ZonedDateTime.now()), format("Error for Future annotation in attribute ZonedDateTime, generated value: %s, the value needs to be grander than actual date", dummy.getFutureZonedDateTime()));
        assertTrue(dummy.getFutureLocalDate().isAfter(LocalDate.now()), format("Error for Future annotation in attribute LocalDate, generated value: %s, the value needs to be grander than actual date", dummy.getFutureLocalDate()));
//        assertTrue(dummy.getFutureLocalTime().isAfter(LocalTime.now()), format("Error for Future annotation in attribute LocalTime, generated value: %s, the value needs to be grander than actual time", dummy.getFutureLocalTime()));
//        assertTrue(dummy.getFutureOffsetTime().isAfter(OffsetTime.now()), format("Error for Future annotation in attribute OffsetTime, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOffsetTime()));
        assertTrue(dummy.getFutureDate().after(new Date()), format("Error for Future annotation in attribute Date, generated value: %s, the value needs to be grander than actual date", dummy.getFutureDate()));
        assertNotNull(dummy.getFutureDateString(), "Error for Future annotation in attribute String");
        assertTrue(dummy.getComplexType().getDeepestType().getFutureLocalDateTime().isAfter(LocalDateTime.now()), format("Error for Future annotation in attribute ComplexType.DeepestType.LocalDateTime, generated value: %s, the value needs to be grander than actual date", dummy.getComplexType().getDeepestType().getFutureLocalDateTime()));

        assertTrue(dummy.getFutureOrPresentLocalDateTime().isAfter(LocalDateTime.now()), format("Error for FutureOrPresent annotation in attribute LocalDateTime, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOrPresentLocalDateTime()));
        assertTrue(dummy.getFutureOrPresentInstant().isAfter(Instant.now()), format("Error for FutureOrPresent annotation in attribute Instant, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOrPresentInstant()));
        assertTrue(dummy.getFutureOrPresentOffsetDateTime().isAfter(OffsetDateTime.now()), format("Error for FutureOrPresent annotation in attribute OffsetDateTime, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOrPresentOffsetDateTime()));
        assertTrue(dummy.getFutureOrPresentZonedDateTime().isAfter(ZonedDateTime.now()), format("Error for FutureOrPresent annotation in attribute ZonedDateTime, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOrPresentZonedDateTime()));
        assertTrue(dummy.getFutureOrPresentLocalDate().isAfter(LocalDate.now()), format("Error for FutureOrPresent annotation in attribute LocalDate, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOrPresentLocalDate()));
//        assertTrue(dummy.getFutureOrPresentLocalTime().isAfter(LocalTime.now()), format("Error for FutureOrPresent annotation in attribute LocalTime, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOrPresentLocalTime()));
//        assertTrue(dummy.getFutureOrPresentOffsetTime().isAfter(OffsetTime.now()), format("Error for FutureOrPresent annotation in attribute OffsetTime, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOrPresentOffsetTime()));
        assertTrue(dummy.getFutureOrPresentDate().after(new Date()), format("Error for FutureOrPresent annotation in attribute Date, generated value: %s, the value needs to be grander than actual date", dummy.getFutureOrPresentDate()));
        assertNotNull(dummy.getFutureOrPresentDateString(), "Error for FutureOrPresent annotation in attribute String");

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

        assertTrue(dummy.getMinInteger() >= 2);
        assertTrue(dummy.getMinDouble() >= 2);
        assertTrue(dummy.getMinLong() >= 2);
        assertTrue(dummy.getMinBigDecimal().longValue() >= 2);
        assertTrue(Long.parseLong(dummy.getMinString()) >= 2);

        assertTrue(dummy.getMaxInteger() <= 10);
        assertTrue(dummy.getMaxLong() <= 10);
        assertTrue(dummy.getMaxDouble() <= 10);
        assertTrue(dummy.getMaxBigDecimal().longValue() <= 10);
        assertTrue(Long.parseLong(dummy.getMaxString()) <= 10);

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
        assertNotEquals(dummyWithArgsContructors.getNumber(), 0);
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
        assertNotEquals(dummyWithArgsContructors.getNumber(), 0);
        assertNotNull(dummyWithArgsContructors.getAnyDouble());
        assertNotNull(dummyWithArgsContructors.getComplexType().getBoolean());
        assertNotNull(dummyWithArgsContructors.getComplexType().getString());
        assertNotNull(dummyWithArgsContructors.getComplexType().getLong());
        assertNotNull(dummyWithArgsContructors.getComplexType().getInteger());
        assertNotNull(dummyWithArgsContructors.getComplexType().getDouble());
        assertNotNull(dummyWithArgsContructors.getComplexType().getDeepestType().getDeepest());

    }

    @Test
    void shouldGenerateWithArrayAttributes() {
        var domainArray = GenericFixture.generate(DomainArray.class);

        assertEquals(1, domainArray.getArrayDouble().length);
        assertEquals(1, domainArray.getArrayDoublePrimitive().length);
        assertEquals(1, domainArray.getArrayLong().length);
        assertEquals(1, domainArray.getArrayLongPrimitive().length);
        assertEquals(1, domainArray.getArrayInteger().length);
        assertEquals(1, domainArray.getArrayIntegerPrimitive().length);
        assertEquals(1, domainArray.getArrayBoolean().length);
        assertEquals(1, domainArray.getArrayBooleanPrimitive().length);
        assertEquals(1, domainArray.getArrayCharacter().length);
        assertEquals(1, domainArray.getArrayCharacterPrimitive().length);
        assertEquals(1, domainArray.getArrayLocalDateTime().length);
        assertEquals(1, domainArray.getArrayOffsetDateTime().length);
        assertEquals(1, domainArray.getArrayCustomEnum().length);
        assertEquals(1, domainArray.getArrayComplexType().length);
        assertEquals(1, domainArray.getArrayLocalDate().length);
        assertEquals(1, domainArray.getArrayLocalTime().length);
        assertEquals(1, domainArray.getArrayOffsetTime().length);
        assertEquals(1, domainArray.getArrayDate().length);
        assertEquals(1, domainArray.getArrayBigDecimal().length);

        assertEquals(1, domainArray.getArrayMapIntegerInteger().length);
        assertEquals(1, domainArray.getArrayMapStringInteger().length);
        assertEquals(1, domainArray.getArrayMapStringCustomEnum().length);
        assertEquals(1, domainArray.getArrayMapCustomEnumString().length);
        assertEquals(1, domainArray.getArrayMapIntegerLocalDateTime().length);
        assertEquals(1, domainArray.getArrayMapCharacterBigDecimal().length);
        assertEquals(1, domainArray.getArrayMapIntegerComplexyType().length);
        assertEquals(1, domainArray.getArrayMapStringComplexyType().length);
        assertEquals(1, domainArray.getArrayMapComplexyTypeString().length);

        assertEquals(1, domainArray.getArrayHashMapIntegerInteger().length);
        assertEquals(1, domainArray.getArrayHashMapStringInteger().length);
        assertEquals(1, domainArray.getArrayHashMapStringCustomEnum().length);
        assertEquals(1, domainArray.getArrayHashMapCustomEnumString().length);
        assertEquals(1, domainArray.getArrayHashMapIntegerLocalDateTime().length);
        assertEquals(1, domainArray.getArrayHashMapCharacterBigDecimal().length);
        assertEquals(1, domainArray.getArrayHashMapIntegerComplexyType().length);
        assertEquals(1, domainArray.getArrayHashMapStringComplexyType().length);

        assertEquals(1, domainArray.getArrayDictionaryIntegerInteger().length);
        assertEquals(1, domainArray.getArrayDictionaryStringInteger().length);
        assertEquals(1, domainArray.getArrayDictionaryStringCustomEnum().length);
        assertEquals(1, domainArray.getArrayDictionaryCustomEnumString().length);
        assertEquals(1, domainArray.getArrayDictionaryIntegerLocalDateTime().length);
        assertEquals(1, domainArray.getArrayDictionaryCharacterBigDecimal().length);
        assertEquals(1, domainArray.getArrayDictionaryIntegerComplexyType().length);
        assertEquals(1, domainArray.getArrayDictionaryStringComplexyType().length);

        assertEquals(1, domainArray.getArrayTreeMapIntegerString().length);
    }

    @Test
    void shouldNotGenerateWhenKeyOfMapIsClassNotImplementsComparable() {
        var exception = assertThrows(RuntimeException.class, () -> GenericFixture.generate(DomainMapError.class));
        assertEquals("It's necessary to implement Comparable<?> Interface in Key Class of the Map: java.util.TreeMap<domain.ComplexType, java.lang.Integer>", exception.getMessage());
    }

    @Test
    void shouldThrowsExceptionWhenClassIsNotImplementedOnGenericFixture() {
        var exception = assertThrows(RuntimeException.class, () -> GenericFixture.generate(ClassNotImplemented.class));
        assertEquals("Type not recognized: java.time.Clock", exception.getMessage());
    }

    @Test
    void shouldContain2Itens() {
        var classWithOnlyIterables = GenericFixture.generate(ClassWithOnlyIterables.class, 2);

        assertEquals(2, classWithOnlyIterables.getSimpleList().size());
        assertEquals(2, classWithOnlyIterables.getSimpleMap().size());
        assertEquals(2, classWithOnlyIterables.getSimpleArray().length);
    }

}
