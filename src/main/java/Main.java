import domain.Dummy;
import enums.CustomEnum;

public class Main {

    public static void main(String[] args) throws Exception {

        Dummy d = GenericFixture.generate(Dummy.class);
        System.out.println(d);

    }

}
