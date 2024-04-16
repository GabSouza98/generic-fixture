package generic.fixture;

import domain.Dummy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerateManyTest {

    @Test
    void validateGenerateMany() {
        List<Dummy> fixtures = GenericFixture.generateMany(Dummy.class, null, null, 3);
        assertEquals(3, fixtures.size());
    }

}
