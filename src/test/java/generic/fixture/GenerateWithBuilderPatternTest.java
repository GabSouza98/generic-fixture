package generic.fixture;

import domain.FewAttributes;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GenerateWithBuilderPatternTest {

    @Test
    void testGenerateWithBuilderPattern() {
        FewAttributes result = GenericFixture.forClass(FewAttributes.class)
                .withCustomFields(Map.of("att2", "custom"))
                .withCustomClass(Map.of(Integer.class, 2))
                .withNumberOfItems(3)
                .generate();

        assertNotNull(result.getAtt1());
        assertEquals(result.getAtt2(), "custom");
        assertEquals(result.getAtt3(), 2);
        assertEquals(result.getAtt4(), 2);
    }
}
