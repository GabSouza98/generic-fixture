import generic.fixture.GenericFixture;
import domain.DummyWithArgsContructors;

public class Main {

    public static void main(String[] args) throws Exception {

        DummyWithArgsContructors dummy = GenericFixture.generate(DummyWithArgsContructors.class);

        System.out.println(dummy);

    }

}
