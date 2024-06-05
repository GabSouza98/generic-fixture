package generic.fixture;

import domain.constructors.ImplicitDefaultConstructor;
import domain.uninstantiable.Abstract;
import domain.uninstantiable.InheritAbstract;
import domain.uninstantiable.Interface;
import exceptions.UninstantiableException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstantiableTest {

    @Test
    void testAbstractClass() {
        assertThrows(UninstantiableException.class, () -> GenericFixture.generate(Abstract.class));
    }

    @Test
    void testInterfaceClass() {
        assertThrows(UninstantiableException.class, () -> GenericFixture.generate(Interface.class));
    }

    @Test
    void testInheritAbstractClass() {
        assertDoesNotThrow(() -> GenericFixture.generate(InheritAbstract.class));
    }

}
