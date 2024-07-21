package generic.fixture;

import domain.constructors.ComplexBusinessLogicConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenerateWithBuilderPatternForInstanceTest {

    @Test
    void testWithoutGenericFixture() {
        ComplexBusinessLogicConstructor myInstance = new ComplexBusinessLogicConstructor("1", "2", 3, 4);
        Assertions.assertNull(myInstance.getAtt5());
    }

    @Test
    void testWithGenericFixture() {
        ComplexBusinessLogicConstructor myInstance = new ComplexBusinessLogicConstructor("1", "2", 3, 4);
        ComplexBusinessLogicConstructor myInstance2 = GenericFixture.forInstance(myInstance).generate();

        Assertions.assertNotNull(myInstance.getAtt5());
    }
}
