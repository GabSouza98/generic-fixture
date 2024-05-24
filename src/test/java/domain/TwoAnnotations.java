package domain;

import generic.fixture.GenericFixture;

import javax.validation.constraints.Max;

public class TwoAnnotations {

   // @Min(2)
    @Max(10)
    private Integer minMaxInteger;

    public static void main(String[] args) {
        TwoAnnotations ta = GenericFixture.generate(TwoAnnotations.class);

        System.out.println(ta);
    }

}
