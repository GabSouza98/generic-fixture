package generic.fixture;

import domain.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class PersonTest {

    // This test validate that it's not possible to generate circular attribute generation
    @Test
    void shouldNotGeneratePersonInsideAttributeAnimals() {

        var result = GenericFixture.generate(Person.class);

        assertNull(result.getAnimals().get(0).getPerson());

    }
}
