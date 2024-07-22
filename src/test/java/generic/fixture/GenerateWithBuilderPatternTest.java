package generic.fixture;

import domain.FewAttributes;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerateWithBuilderPatternTest {

    @Test
    void testGenerateWithBuilderPattern() {
        FewAttributes result = GenericFixture.forClass(FewAttributes.class)
                .withCustomFields(Map.of("att2", "custom"))
                .withCustomClass(Map.of(Integer.class, 2))
                .withNumberOfIterables(3)
                .generate();

        assertNotNull(result.getAtt1());
        assertEquals(result.getAtt2(), "custom");
        assertEquals(result.getAtt3(), 2);
        assertEquals(result.getAtt4(), 2);
    }

    @Test
    void testGenerateWithBuilderPatternAndAssertCustomFieldsPrecedence() {
        FewAttributes result = GenericFixture.forClass(FewAttributes.class)
                .withCustomFields(Map.of("att2", "FIELD"))
                .withCustomClass(Map.of(String.class, "CLASS"))
                .generate();

        assertEquals(result.getAtt1(), "CLASS");
        assertEquals(result.getAtt2(), "FIELD");
    }

    @Test
    void testGenerateWithBuilderPatternAndCustomMap() {
        CustomMap customMap = new CustomMap();
        customMap.put(String.class, "1");
        customMap.put(Integer.class, 2);

        FewAttributes result = GenericFixture.forClass(FewAttributes.class)
                .withCustomClass(customMap)
                .generate();

        assertEquals(result.getAtt1(), "1");
        assertEquals(result.getAtt2(), "1");
        assertEquals(result.getAtt3(), 2);
        assertEquals(result.getAtt4(), 2);
    }

    @Test
    void testGenerateWithBuilderPatternGenerateWithDefaultItems() {
        FewAttributes result = GenericFixture.forClass(FewAttributes.class).generate();

        assertEquals(1, result.getDoubleList().size());
    }

    @Test
    void testGenerateWithBuilderPatternGenerateWithCustomItems() {
        FewAttributes result = GenericFixture.forClass(FewAttributes.class)
                .withNumberOfIterables(3)
                .generate();

        assertEquals(3, result.getDoubleList().size());
    }

    @Test
    void testGenerateWithBuilderPatternGenerateManyDefault() {
        List<FewAttributes> result = GenericFixture.forClass(FewAttributes.class)
                .withCustomFields(Map.of("att2", "FIELD"))
                .withCustomClass(Map.of(String.class, "CLASS"))
                .generateMany();

        assertEquals(1, result.size());
    }

    @Test
    void testGenerateWithBuilderPatternGenerateManyCustomFixtures() {
        List<FewAttributes> result = GenericFixture.forClass(FewAttributes.class)
                .withCustomFields(Map.of("att2", "FIELD"))
                .withCustomClass(Map.of(String.class, "CLASS"))
                .withNumberOfIterables(5)
                .withNumberOfFixtures(3)
                .generateMany();

        assertEquals(3, result.size());
    }
}
