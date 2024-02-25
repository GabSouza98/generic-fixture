package generic.fixture;

import domain.ClassWithAllArgumentsConstructor;
import domain.Dummy;
import domain.DummyWithArgsContructors;
import generic.fixture.GenericFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GenericFixtureInstantiateWithArgumentsTest {

    //TODO: Esse aqui precisa ?
    @Test
    void shouldInstantiateSuccess() {
        assertDoesNotThrow(() -> GenericFixture.generate(Dummy.class));
    }

    @Test
    void shouldInstantiateWithSuccess() {
        assertDoesNotThrow(() -> GenericFixture.generate(DummyWithArgsContructors.class));
    }

    @Test
    @DisplayName("Should instantiate class with success when has attribute class with all args constructor")
    void shouldInstantiateSuccess3() {
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class));
        System.out.println(fixture);
    }

    @Test
    void shouldInstantiateWithSuccessWhenHasCustomFields() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("complexType", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getComplexType());
    }

    @Test
    void shouldInstantiateWithSuccessWhenHasCustomFields2() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("complexType.String", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getComplexType().getString());
    }

    @Test
    @DisplayName("should instantiate with success when has custom fields null for attribute class with args constructor")
    void shouldInstantiateWithSuccessWhenHasCustomFields3() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("dummyWithArgsContructors", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getDummyWithArgsContructors());
    }

    @Test
    @DisplayName("should instantiate with success when has custom fields null for attribute inside class with args constructor")
    void shouldInstantiateWithSuccessWhenHasCustomFields4() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("dummyWithArgsContructors.name", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getDummyWithArgsContructors().getName());
    }

    @Test
    @DisplayName("should instantiate with success when has custom fields null for deepestType")
    void shouldInstantiateWithSuccessWhenHasCustomFields5() {
        Map<String, Object> customFields = new HashMap<>();
        customFields.put("dummyWithArgsContructors.complexType.DeepestType.deepest", null);
        ClassWithAllArgumentsConstructor fixture = assertDoesNotThrow(() -> GenericFixture.generate(ClassWithAllArgumentsConstructor.class, customFields));
        assertNull(fixture.getDummyWithArgsContructors().getComplexType().getDeepestType().getDeepest());
    }
}
