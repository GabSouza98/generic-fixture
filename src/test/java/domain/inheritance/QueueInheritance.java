package domain.inheritance;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayDeque;

@Getter
@Setter
@ToString
public class QueueInheritance extends ArrayDeque<Thing> {
}
