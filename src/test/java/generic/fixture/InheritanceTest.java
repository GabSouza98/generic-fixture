package generic.fixture;

import domain.inheritance.ArrayChild;
import domain.inheritance.ArrayInheritance;
import domain.inheritance.ArrayInheritanceWithArrayAttribute;
import domain.inheritance.CompositionWithInheritance;
import domain.inheritance.MapInheritance;
import domain.inheritance.QueueInheritance;
import domain.inheritance.SecondChild;
import domain.inheritance.SetInheritance;
import domain.inheritance.Withdrawal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void testWithdrawal() {
        Withdrawal withdrawal = assertDoesNotThrow(() -> GenericFixture.generate(Withdrawal.class, 1));
        assertEquals(1, withdrawal.getArrayInheritance().size());
        assertNotNull(withdrawal.getArrayInheritance().getAttribute1());
    }

    @Test
    void testWithdrawalWith3Items() {
        Withdrawal withdrawal = assertDoesNotThrow(() -> GenericFixture.generate(Withdrawal.class, 3));
        assertEquals(3, withdrawal.getArrayInheritance().size());
        assertNotNull(withdrawal.getArrayInheritance().getAttribute1());
    }

    @Test
    void testArrayInheritance() {
        ArrayInheritance arrayInheritance = assertDoesNotThrow(() -> GenericFixture.generate(ArrayInheritance.class));
        assertEquals(1, arrayInheritance.size());
        assertNotNull(arrayInheritance.getAttribute1());
    }

    @Test
    void testArrayInheritanceWith3Items() {
        ArrayInheritance arrayInheritance = assertDoesNotThrow(() -> GenericFixture.generate(ArrayInheritance.class, 3));
        assertEquals(3, arrayInheritance.size());
        assertNotNull(arrayInheritance.getAttribute1());
    }

    @Test
    void testArrayChild() {
        ArrayChild arrayChild = assertDoesNotThrow(() -> GenericFixture.generate(ArrayChild.class));
        assertEquals(1, arrayChild.size());
        assertNotNull(arrayChild.getAttribute1());
        assertNotNull(arrayChild.getAttribute2());
    }

    @Test
    void testSetInheritance() {
        SetInheritance setInheritance = assertDoesNotThrow(() -> GenericFixture.generate(SetInheritance.class, 3));
        assertEquals(3, setInheritance.size());
    }

    @Test
    void testQueueInheritance() {
        QueueInheritance queueInheritance = assertDoesNotThrow(() -> GenericFixture.generate(QueueInheritance.class, 3));
        assertEquals(3, queueInheritance.size());
    }

    @Test
    void testMapInheritance() {
        MapInheritance mapInheritance = assertDoesNotThrow(() -> GenericFixture.generate(MapInheritance.class, 3));
        assertEquals(3, mapInheritance.size());
    }

    @Test
    void ArrayInheritanceWithArrayAttribute() {
        ArrayInheritanceWithArrayAttribute arrayWithAtt = assertDoesNotThrow(() -> GenericFixture.generate(ArrayInheritanceWithArrayAttribute.class, 3));
        assertEquals(3, arrayWithAtt.size());
        assertEquals(3, arrayWithAtt.getArrayList().size());
        assertEquals(3, arrayWithAtt.getArrayChild().size());

        assertNotNull(arrayWithAtt.get(0).getThing1());
        assertNotNull(arrayWithAtt.get(0).getThing2());

        assertNotNull(arrayWithAtt.getArrayList().get(0).getThing1());
        assertNotNull(arrayWithAtt.getArrayList().get(0).getThing2());

        assertNotNull(arrayWithAtt.getArrayChild().getAttribute1());
        assertNotNull(arrayWithAtt.getArrayChild().getAttribute2());
        assertNotNull(arrayWithAtt.getArrayChild().get(0).getThing1());
        assertNotNull(arrayWithAtt.getArrayChild().get(0).getThing2());
    }

}
