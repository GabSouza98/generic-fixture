package generic.fixture;

import domain.constructors.ComplexBusinessLogicConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GenerateWithBuilderPatternForInstanceTest {

    @Test
    void testWithoutGenericFixture() {
        ComplexBusinessLogicConstructor myInstance = new ComplexBusinessLogicConstructor("1", "2", 3, 4);
        assertNull(myInstance.getAtt5());
    }

    @Test
    void testWithGenericFixture() {
        ComplexBusinessLogicConstructor myInstance = new ComplexBusinessLogicConstructor("1", "2", 3, 4);
        ComplexBusinessLogicConstructor myInstance2 = GenericFixture.forInstance(myInstance).generate();

        assertNotNull(myInstance.getAtt5());
        assertNotNull(myInstance2.getAtt5());
        assertSame(myInstance, myInstance2);
    }
}
