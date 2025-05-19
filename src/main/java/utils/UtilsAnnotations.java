package utils;

import enums.AnnotationsEnum;

import jakarta.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.util.Map;

public class UtilsAnnotations {

    public static boolean hasAnnotation(Map<AnnotationsEnum, Annotation> hashMap) {
        return !hashMap.isEmpty();
    }

    public static int limitateDefaultMaxValue(Size size) {
        return size.max() == Integer.MAX_VALUE ? size.min() : size.max();
    }


}
