package domain.inheritance;

import domain.ComplexType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Parent {
    private ComplexType ParentComplexType;
    private String ParentString;
    private Long ParentLong;
}
