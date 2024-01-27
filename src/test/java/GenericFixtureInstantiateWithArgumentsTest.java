import domain.ClassWithAllArgumentsConstructor;
import domain.Dummy;
import domain.DummyWithArgsContructors;
import generic.fixture.GenericFixture;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GenericFixtureInstantiateWithArgumentsTest {

    @Test
    void shouldInstantiateSuccess() {
        assertDoesNotThrow(() -> GenericFixture.generate(Dummy.class));
    }

    @Test
    void shouldInstantiateSuccess2() {
        assertDoesNotThrow(() -> GenericFixture.generate(DummyWithArgsContructors.class));
    }

    @Test
    void shouldInstantiateSuccess3() {
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class));
        System.out.println(fixture);
    }

    @Test
    void shouldInstantiateSuccess4() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("complexType", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getComplexType());
    }

    @Test
    void shouldInstantiateSuccess5() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("complexType.String", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getComplexType().getString());
    }

    @Test
    void shouldInstantiateSuccess6() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("dummyWithArgsContructors", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getDummyWithArgsContructors());
    }

    @Test
    void shouldInstantiateSuccess7() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("dummyWithArgsContructors.name", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getDummyWithArgsContructors().getName());
    }

    @Test
    void shouldInstantiateSuccess8() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("dummyWithArgsContructors.complexType.DeepestType.deepest", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getDummyWithArgsContructors().getComplexType().getDeepestType().getDeepest());
    }
}
