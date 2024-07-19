package generic.fixture;

import domain.FewAttributes;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerateWithCustomClassTest {

    @Test
    void testInstantiationOfClassWithGenerateWithCustomClass1() {
        var result = GenericFixture.generateWithCustomClass(FewAttributes.class, Map.of(String.class, ""));
        assertEquals(result.getAtt1(), "");
        assertEquals(result.getAtt2(), "");
    }

    @Test
    void testInstantiationOfClassWithGenerateWithCustomClass2() {
        var result = GenericFixture.generateWithCustomClass(FewAttributes.class, Map.of(Integer.class, 1));
        assertEquals(result.getAtt3(), 1);
        assertEquals(result.getAtt4(), 1);
    }

    @Test
    void testInstantiationOfClassWithGenerateWithCustomClass3() {
        var result = GenericFixture.generateWithCustomClass(FewAttributes.class, Map.of(String.class, "", Integer.class, 2));
        assertEquals(result.getAtt1(), "");
        assertEquals(result.getAtt2(), "");
        assertEquals(result.getAtt3(), 2);
        assertEquals(result.getAtt4(), 2);
    }

    @Test
    void testInstantiationOfClassWithGenerateWithCustomMap() {
        //Using CustomMap enforces type-safety within entries
        CustomMap customMap = new CustomMap();
        customMap.put(String.class, "");
        customMap.put(Integer.class, 2);
        var result = GenericFixture.generateWithCustomClass(FewAttributes.class, customMap);
        assertEquals(result.getAtt1(), "");
        assertEquals(result.getAtt2(), "");
        assertEquals(result.getAtt3(), 2);
        assertEquals(result.getAtt4(), 2);
    }

}
