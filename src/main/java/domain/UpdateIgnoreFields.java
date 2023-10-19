package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateIgnoreFields {

    //Este método é para remover o que vem antes do primeiro ponto, exemplo
    // complexyType.String
    // Após o método ficará
    // String
    public static List<String> update(List<String> ignoredFields) {
        List<String> updateList = new ArrayList<>();
        ignoredFields
                .forEach(ignored -> {
                    if(ignored.contains("."))
                        updateList.add(ignored.split("\\.", 2)[1]);
                });
        return updateList;
    }

    public static void main(String[] args) {
        List<String> result1 = update(Arrays.asList("campo.campoInterno.maisUmTroco.pizzol"));
        System.out.println(result1);

        List<String> result2 = update(result1);
        System.out.println(result2);

        List<String> result3 = update(result2);
        System.out.println(result3);

        List<String> result4 = update(result3);
        System.out.println(result4);
    }

}
