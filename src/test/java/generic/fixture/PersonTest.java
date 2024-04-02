package generic.fixture;

import domain.circular.generation.Person;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test
    void shouldGenerateClassWithAttributesOfSameClass() {

        var person = GenericFixture.generate(Person.class);

        assertNotNull(person.getBankTwo());
        assertNotNull(person.getBankOne());
        assertNotNull(person.getAnimals().get(0).getPetShop().getEmployeed().getBankOne());
        assertNotNull(person.getAnimals().get(0).getPetShop().getEmployeed().getBankTwo());

    }

    @Test
    void shouldSetAllListWithSizeTwoWithCircularAttributesNull() {

        var person = GenericFixture.generate(Person.class, 2);

        assertNull(person.getAnimals().get(0).getPerson());
        assertNull(person.getAnimals().get(0).getPetShop().getAnimals().get(0).getPerson());
        assertNull(person.getAnimals().get(0).getPetShop().getAnimals().get(0).getPetShop());
        assertNull(person.getAnimals().get(0).getPetShop().getEmployeed().getPerson());
        assertNull(person.getAnimals().get(0).getPetShop().getEmployeed().getPetShop());
        assertNull(person.getAnimals().get(0).getPetShop().getEmployeed().getAnimal().getPerson());
        assertNull(person.getAnimals().get(0).getPetShop().getEmployeed().getAnimal(). getPetShop());

        assertNull(person.getAnimals().get(1).getPerson());
        assertNull(person.getAnimals().get(1).getPetShop().getAnimals().get(1).getPerson());
        assertNull(person.getAnimals().get(1).getPetShop().getAnimals().get(1).getPetShop());
        assertNull(person.getAnimals().get(1).getPetShop().getEmployeed().getPerson());
        assertNull(person.getAnimals().get(1).getPetShop().getEmployeed().getPetShop());
        assertNull(person.getAnimals().get(1).getPetShop().getEmployeed().getAnimal().getPerson());
        assertNull(person.getAnimals().get(1).getPetShop().getEmployeed().getAnimal(). getPetShop());

        assertEquals(2, person.getAnimals().size());
        assertEquals(2, person.getAnimals().get(0).getPetShop().getAnimals().size());
        assertEquals(2, person.getAnimals().get(1).getPetShop().getAnimals().size());
    }

}
