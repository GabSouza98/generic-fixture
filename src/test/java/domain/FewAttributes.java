package domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FewAttributes {
    private String att1;
    private String att2;
    private Integer att3;
    private Integer att4;
}
