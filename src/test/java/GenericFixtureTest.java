import domain.ComplexType;
import domain.Dummy;
import domain.DummyWithArgsContructors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class GenericFixtureTest {

    private static final int NULL_INT_PRIMITIVE_IS_ZERO = 0;

    Dummy dummy;

    @BeforeEach
    void setUp() throws Exception {
        //dummy = GenericFixture.generate(Dummy.class);
    }

    @Test
    void shouldValidateAllFields() {
        assertTrue(dummy.getPrimitiveInt() != 0, "Erro para o tipo int");
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
        assertTrue(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", dummy.getStringWithDateFormat()), "Erro para formatar no pattern");
        assertTrue(dummy.getMinimumString().length() >= 3, "Erro para a anotação Size(min)");
        assertTrue(dummy.getMaximumString().length() <= 6, "Erro para a anotação Size(max)");
        assertTrue(dummy.getMediumString().length() >= 4, "Erro para a anotação Size(min, max)");
        assertTrue(dummy.getMediumString().length() <= 9, "Erro para a anotação Size(min, max)");
    }

//    @Test
//    void shouldThrowExceptionWhenDoesntHasNoArgsConstructor() {
//        assertThrows(Exception.class, () -> GenericFixture.generate(DummyWithArgsContructors.class));
//    }

    @Test
    void shouldIgnoreSomeFields() throws Exception {
        //Como ignorar campos da ComplexType ?
       // Dummy dummyWithIgnoreFields = GenericFixture.generate(Dummy.class, Arrays.asList("primitiveInt", "ComplexType", "stringMap"));

        Dummy dummyWithIgnoreFields = GenericFixture.generate(Dummy.class, Arrays.asList("ComplexType.String"));

    //    assertEquals(NULL_INT_PRIMITIVE_IS_ZERO, dummyWithIgnoreFields.getPrimitiveInt());
        //assertNull(dummyWithIgnoreFields.getComplexType());
    //    assertNull(dummyWithIgnoreFields.getStringMap());

        assertNotNull(dummy.getString(), "Erro para o tipo String");
        assertNotNull(dummy.getInteger(), "Erro para o tipo Integer");
        assertNotNull(dummy.getDouble(), "Erro para o tipo Double");
        assertNotNull(dummy.getLocalDateTime(), "Erro para o tipo LocalDateTime");
        assertNotNull(dummy.getOffsetDateTime(), "Erro para o tipo OffsetDateTime");
        assertNotNull(dummy.getComplexList(), "Erro para o tipo complexList");
        assertNotNull(dummy.getComplexList().get(0), "Erro para inserir registro na complexList");
        assertNotNull(dummy.getIntegerList(), "Erro para o tipo integerList");
        assertNotNull(dummy.getDoubleList(), "Erro para o tipo doubleList");
        assertNotNull(dummy.getStringList(), "Erro para o tipo stringList");
        assertNotNull(dummy.getCharList(), "Erro para o tipo charList");
        assertNotNull(dummy.getBooleanList(), "Erro para o tipo booleanList");
        assertNotNull(dummy.getCustomEnum(), "Erro para o tipo CustomEnum");
        assertNotNull(dummy.getIntegerMap(), "Erro para o tipo integerMap");
        assertNotNull(dummy.getMixedMap(), "Erro para o tipo mixedMap");
        assertNotNull(dummy.getComplexTypeMap(), "Erro para o tipo complexMap");
        assertEquals(1, dummy.getStringMap().size(), "Erro para inserir no CustomMap");
    }

}