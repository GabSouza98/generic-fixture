package domain.circular.generation;

import domain.DeepestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Worker {

    private String name;
    private int age;
    private DeepestType deepestType;
    private PetShop petShop;
    private Person person;
    private Animal animal;
    private Bank bankOne;
    private Bank bankTwo;

}
