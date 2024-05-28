package domain.inheritance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class ArrayInheritanceWithArrayAttribute extends ArrayList<Thing> {
    private ArrayList<Thing> arrayList;
    private ArrayChild arrayChild;
}
