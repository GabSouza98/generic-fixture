package generic.fixture;

import domain.constructors.ExplicitDefaultConstructor;
import domain.constructors.ImplicitDefaultConstructor;
import domain.constructors.InheritConstructor;
import domain.constructors.PrivateConstructor;
import domain.constructors.ProtectedConstructor;
import exceptions.NoSuitableConstructorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConstructorsTest {

    @Test
    void testExplicitDefaultConstructor() {
        ExplicitDefaultConstructor defaultCons = assertDoesNotThrow(() -> GenericFixture.generate(ExplicitDefaultConstructor.class));
        assertNotNull(defaultCons.getFieldB());
    }

    @Test
    void testImplicitDefaultConstructor() {
        assertDoesNotThrow(() -> GenericFixture.generate(ImplicitDefaultConstructor.class));
    }

    @Test
    void testInheritConstructor() {
        assertDoesNotThrow(() -> GenericFixture.generate(InheritConstructor.class));
    }

    @Test
    void testProtectedConstructor() {
        assertThrows(NoSuitableConstructorException.class, () -> GenericFixture.generate(ProtectedConstructor.class));
    }

    @Test
    void testPrivateConstructor() {
        assertThrows(NoSuitableConstructorException.class, () -> GenericFixture.generate(PrivateConstructor.class));
    }

}
