package generic.fixture;

import domain.inheritance.CompositionWithInheritance;
import domain.inheritance.SecondChild;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InheritanceTest {

    @Test
    void testInheritance() {
        SecondChild fixture = GenericFixture.generate(SecondChild.class);
        assertNotNull(fixture.getParentComplexType());
        assertNotNull(fixture.getParentLong());
        assertNotNull(fixture.getParentString());
        assertNotNull(fixture.getFirstChildBoolean());
        assertNotNull(fixture.getFirstChildDouble());
        assertNotNull(fixture.getFirstChildLocalDateTime());
        assertNotNull(fixture.getSecondChildDouble());
        assertNotNull(fixture.getSecondChildInt());
    }

    @Test
    void testCompositionWithInheritance() {
        CompositionWithInheritance fixture = GenericFixture.generate(CompositionWithInheritance.class);
        assertNotNull(fixture.getSecondChild().getParentComplexType());
        assertNotNull(fixture.getSecondChild().getParentLong());
        assertNotNull(fixture.getSecondChild().getParentString());
        assertNotNull(fixture.getSecondChild().getFirstChildBoolean());
        assertNotNull(fixture.getSecondChild().getFirstChildDouble());
        assertNotNull(fixture.getSecondChild().getFirstChildLocalDateTime());
        assertNotNull(fixture.getSecondChild().getSecondChildDouble());
        assertNotNull(fixture.getSecondChild().getSecondChildInt());
        assertNotNull(fixture.getName());
    }

}
