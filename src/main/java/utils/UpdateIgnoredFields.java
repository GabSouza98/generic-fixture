package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateIgnoredFields {

    /**
     * This method receives a list of Strings in the format
     * "A.B.C", and removes the word before the first dot. In the example,
     * it would return only "B.C". This happens because the parameter "2"
     * in the split method truncates que resulting String[] with a size
     * of 2, hence, dividing the original string only at the first dot.
     * If the original string doesn't contain any dots, an empty Arraylist
     * will be returned.
     *
     * @param ignoredFields -> List containing String in the "A.B.C" format
     * @return -> Updated list without first word before dot
     */

    public static List<String> update(List<String> ignoredFields, String fieldName) {
        List<String> updatedList = new ArrayList<>();
        ignoredFields.forEach(ignored -> {
            if (ignored.startsWith(fieldName)) {
                if (ignored.contains(".")) {
                    updatedList.add(ignored.split("\\.", 2)[1]);
                }
            } else {
                updatedList.add(ignored);
            }
        });
        return updatedList;
    }

}
