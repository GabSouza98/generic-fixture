import domain.Dummy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class GenericFixtureTest {

    Dummy dummy;

    @BeforeEach
    void setUp() throws Exception {
        dummy = GenericFixture.generate(Dummy.class);
    }

    @Test
    void shouldValidateAllFields() throws Exception {
        assertTrue(dummy.getPrimitiveInt() != 0, "Erro para o tipo int");
        assertNotNull(dummy.getString(), "Erro para o tipo String");
        assertNotNull(dummy.getInteger(), "Erro para o tipo Integer");
        assertNotNull(dummy.getDouble(), "Erro para o tipo Double");
        assertNotNull(dummy.getLocalDateTime(), "Erro para o tipo LocalDateTime");
        assertNotNull(dummy.getOffsetDateTime(), "Erro para o tipo OffsetDateTime");
        assertNotNull(dummy.getComplexType(), "Erro para o tipo ComplexType");
        assertNotNull(dummy.getList(), "Erro para o tipo List");
        assertNotNull(dummy.getList().get(0), "Erro para inserir registro na lista");
        assertNotNull(dummy.getCustomEnum(), "Erro para o tipo CustomEnum");
        assertNotNull(dummy.getCustomMap(), "Erro para o tipo CustomMap");
        assertEquals(1, dummy.getCustomMap().size(), "Erro para inserir no CustomMap");
    }

    @Test
    void shouldTestFieldWithAnnotations() throws Exception {

        assertTrue(Pattern.matches("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$", dummy.getStringWithDateFormat()), "Erro para formatar no pattern");
        assertTrue(dummy.getMinimumString().length() >= 3, "Erro para a anotação Size(min)");
        assertTrue(dummy.getMaximumString().length() <= 6, "Erro para a anotação Size(max)");
        assertTrue(dummy.getMediumString().length() >= 4, "Erro para a anotação Size(min, max)");
        assertTrue(dummy.getMediumString().length() <= 9, "Erro para a anotação Size(min, max)");
    }

}