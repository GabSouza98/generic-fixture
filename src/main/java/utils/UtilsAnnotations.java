package utils;

import enums.AnnotationsEnum;

import java.lang.annotation.Annotation;
import java.util.Map;

public class UtilsAnnotations {

    public static boolean hasAnnotation(Map<AnnotationsEnum, Annotation> hashMap) {
        return !hashMap.isEmpty();
    }

}
