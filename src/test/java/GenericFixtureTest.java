import domain.ComplexType;
import domain.Dummy;
import domain.DummyWithArgsContructors;
import exceptions.NoArgsConstructorException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class GenericFixtureTest {

    public static final String NAME = "Da Silva";
    public static final Integer NUMBER_TEN = 10;
    HashMap<String, Object> customFields = new HashMap<>();

    @Test
    void shouldValidateAllFields() {
        Dummy dummy = GenericFixture.generate(Dummy.class);
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
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class,customFields);

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

        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class,customFields);

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