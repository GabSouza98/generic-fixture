package generic.fixture;

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

}
