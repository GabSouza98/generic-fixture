package domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DomainMapError {

    private Map<ComplexType, Integer>[] arrayMapComplexyTypeInteger;
    private Map<ComplexType, String>[] arrayMapComplexyTypeString;
    private Map<ComplexType, ComplexType>[] arrayMapComplexyTypeComplexyType;

}
