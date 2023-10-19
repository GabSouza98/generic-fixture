import domain.Dummy;
import domain.DummyWithArgsContructors;
import exceptions.NoArgsConstructorException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class GenericFixtureTest {

    private static final int NULL_INT_PRIMITIVE_IS_ZERO = 0;

    @Test
    void shouldValidateAllFields() throws Exception {
        Dummy dummy = GenericFixture.generate(Dummy.class);
        assertTrue(dummy.getPrimitiveInt() != 0, "Erro para o tipo int");//Esta falhando as vezes
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
    void shouldTestFieldWithAnnotations() throws Exception {
        Dummy dummy = GenericFixture.generate(Dummy.class);
        assertTrue(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", dummy.getStringWithDateFormat()), "Erro para formatar no pattern");
        assertTrue(dummy.getMinimumString().length() >= 3, "Erro para a anotação Size(min)");
        assertTrue(dummy.getMaximumString().length() <= 6, "Erro para a anotação Size(max)");
        assertTrue(dummy.getMediumString().length() >= 4, "Erro para a anotação Size(min, max)");
        assertTrue(dummy.getMediumString().length() <= 9, "Erro para a anotação Size(min, max)");
    }

    @Test
    void shouldThrowExceptionWhenDoesntHasNoArgsConstructor() {
        assertThrows(NoArgsConstructorException.class, () -> GenericFixture.generate(DummyWithArgsContructors.class));
    }

    @Test
    void shouldIgnore_String_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Collections.singletonList("String"));

        assertNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_ComplexType_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Collections.singletonList("ComplexType"));

        assertNull(dummyWithIgnoredFields.getComplexType());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_ComplexType_String_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Collections.singletonList("ComplexType.String"));

        assertNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNotNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_complexList_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Collections.singletonList("complexList"));

        assertNull(dummyWithIgnoredFields.getComplexList());
        assertNotNull(dummyWithIgnoredFields.getComplexType());
    }

    @Test
    void shouldIgnore_complexList_String_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Collections.singletonList("complexList.String"));

        assertNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
        assertNotNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getString());
    }

    @Test
    void shouldIgnore_ComplexType_And_String_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Arrays.asList("ComplexType", "String"));

        assertNull(dummyWithIgnoredFields.getComplexType());
        assertNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_ComplexTypeString_And_String_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Arrays.asList("ComplexType.String", "String"));

        assertNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_complexListString_And_String_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Arrays.asList("complexList.String", "String"));

        assertNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
        assertNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getString());
    }

    @Test
    void shouldIgnore_ComplexTypeString_And_complexListString_And_String_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Arrays.asList("ComplexType.String", "String", "complexList.String"));

        assertNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNull(dummyWithIgnoredFields.getString());
        assertNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
    }

    @Test
    void shouldIgnore_ComplexTypeString_And_complexListString_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Arrays.asList("ComplexType.String", "complexList.String"));

        assertNull(dummyWithIgnoredFields.getComplexType().getString());
        assertNull(dummyWithIgnoredFields.getComplexList().get(0).getString());
        assertNotNull(dummyWithIgnoredFields.getString());
    }

    @Test
    void shouldIgnore_Integer_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Collections.singletonList("Integer"));

        assertNull(dummyWithIgnoredFields.getInteger());
        assertNotNull(dummyWithIgnoredFields.getString());
        assertNotNull(dummyWithIgnoredFields.getComplexType().getInteger());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getInteger());
        assertNotNull(dummyWithIgnoredFields.getIntegerList().get(0));
    }

    @Test
    void shouldIgnore_ComplexTypeInteger_Field() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Collections.singletonList("ComplexType.Integer"));

        assertNull(dummyWithIgnoredFields.getComplexType().getInteger());
        assertNotNull(dummyWithIgnoredFields.getInteger());
        assertNotNull(dummyWithIgnoredFields.getComplexList().get(0).getInteger());
        assertNotNull(dummyWithIgnoredFields.getIntegerList().get(0));
    }

    @Test
    void shouldIgnore_ComplexTypeInteger_Field22() throws Exception {
        Dummy dummyWithIgnoredFields = GenericFixture.generate(Dummy.class, Collections.singletonList("ComplexType.DeepestType.deepest"));

        assertNull(dummyWithIgnoredFields.getComplexType().getDeepestType().getDeepest());
    }

}