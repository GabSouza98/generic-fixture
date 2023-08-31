import domain.Dummy;
import enums.CustomEnum;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) throws Exception {

        Dummy d = GenericFixture.generate(Dummy.class);
        System.out.println(d);

        //para usar depois
//        System.out.println(ThreadLocalRandom.current().nextDouble(0, 1));

    }

}
