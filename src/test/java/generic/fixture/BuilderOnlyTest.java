package generic.fixture;

import domain.BuilderOnly;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BuilderOnlyTest {

    @Test
    void testInstantiationOfClassWithBuilderOnly() {
        var builderOnly = GenericFixture.generate(BuilderOnly.class);
        assertNotNull(builderOnly);
    }

}
