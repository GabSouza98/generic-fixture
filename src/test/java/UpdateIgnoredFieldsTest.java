import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.UpdateIgnoredFields.update;

public class UpdateIgnoredFieldsTest {

    @Test
    void shouldUpdateIgnorableFieldsHierarchy() {
        // Arrange & Act
        List<String> result1 = update(Collections.singletonList("A.B.C.D"), "A");
        List<String> result2 = update(result1, "B");
        List<String> result3 = update(result2, "C");
        List<String> result4 = update(result3, "D");

        // Assert
        assertEquals("B.C.D", result1.get(0));
        assertEquals("C.D", result2.get(0));
        assertEquals("D", result3.get(0));
        assertTrue(result4.isEmpty());
    }

    @Test
    void shouldUpdateIgnorableFieldsHierarchyWithoutInterferingInOtherItens() {
        // Arrange & Act
        List<String> initial = Arrays.asList("A.B.C.D", "E.F", "G");
        List<String> result1 = update(initial, "A");
        List<String> result2 = update(result1, "B");
        List<String> result3 = update(result2, "C");
        List<String> result4 = update(result3, "D");
        List<String> result5 = update(result4, "E");
        List<String> result6 = update(result5, "F");
        List<String> result7 = update(result6, "G");

        // Assert
        assertEquals(3, result1.size());
        assertEquals("B.C.D", result1.get(0));
        assertEquals("E.F", result1.get(1));
        assertEquals("G", result1.get(2));

        assertEquals(3, result2.size());
        assertEquals("C.D", result2.get(0));
        assertEquals("E.F", result2.get(1));
        assertEquals("G", result2.get(2));

        assertEquals(3, result3.size());
        assertEquals("D", result3.get(0));
        assertEquals("E.F", result3.get(1));
        assertEquals("G", result3.get(2));

        assertEquals(2, result4.size());
        assertEquals("E.F", result4.get(0));
        assertEquals("G", result4.get(1));

        assertEquals(2, result5.size());
        assertEquals("F", result5.get(0));
        assertEquals("G", result5.get(1));

        assertEquals(1, result6.size());
        assertEquals("G", result6.get(0));

        assertEquals(0, result7.size());
    }

}
