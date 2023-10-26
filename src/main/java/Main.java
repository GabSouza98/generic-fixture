import domain.Dummy;

public class Main {

    public static void main(String[] args) throws Exception {

        Dummy dummy = GenericFixture.generate(Dummy.class);
        System.out.println(dummy);
        //para usar depois
//        System.out.println(ThreadLocalRandom.current().nextDouble(0, 1));

    }

}
