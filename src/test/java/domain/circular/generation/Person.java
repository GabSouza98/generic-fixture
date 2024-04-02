package domain.circular.generation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {

    private String name;
    private Bank bankOne;
    private List<Animal> animals;
    private Bank bankTwo;

}
