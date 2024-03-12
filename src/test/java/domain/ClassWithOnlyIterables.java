package domain;

import lombok.*;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClassWithOnlyIterables {
    private Map<Integer, Integer> simpleMap;
    private ArrayList<Integer> simpleList;
    private Integer[] simpleArray;
}
