package domain.inheritance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;

@Getter
@Setter
@ToString
public class SetInheritance extends HashSet<Thing> {
}
