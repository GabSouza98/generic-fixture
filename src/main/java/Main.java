import domain.Dummy;
import domain.DummyWithArgsContructors;
import sun.net.www.content.text.Generic;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws Exception {

        DummyWithArgsContructors dummy = GenericFixture.generate(DummyWithArgsContructors.class);

        System.out.println(dummy);

    }

}
