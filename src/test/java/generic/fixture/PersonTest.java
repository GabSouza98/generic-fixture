package generic.fixture;

import domain.circular.generation.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class PersonTest {

    // This test validate that it's not possible to generate circular attribute generation and doesn't throws StackOverFlowException
    @Test
    void shouldSetNullIntAttriutesWhenAttributesIsOfCircularGeneration() {

        var person = GenericFixture.generate(Person.class);

        assertNull(person.getAnimals().get(0).getPerson());
        assertNull(person.getAnimals().get(0).getPetShop().getAnimals().get(0).getPerson());
        assertNull(person.getAnimals().get(0).getPetShop().getAnimals().get(0).getPetShop());
        assertNull(person.getAnimals().get(0).getPetShop().getEmployeed().getPerson());
        assertNull(person.getAnimals().get(0).getPetShop().getEmployeed().getPetShop());
        assertNull(person.getAnimals().get(0).getPetShop().getEmployeed().getAnimal().getPerson());
        assertNull(person.getAnimals().get(0).getPetShop().getEmployeed().getAnimal(). getPetShop());

    }
}
