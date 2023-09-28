import com.github.curiousoddman.rgxgen.RgxGen;
import enums.AnnotationsEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static enums.AnnotationsEnum.PATTERN;
import static enums.AnnotationsEnum.SIZE;
import static java.util.Objects.nonNull;

public class GenericFixture {

    static SecureRandom r = new SecureRandom();

    public static <T> T generate(Class<T> clazz) throws Exception {

        try {

            T type = clazz.getDeclaredConstructor().newInstance();

            Field[] fields = type.getClass().getDeclaredFields();

            List<Field> fieldsList = ignoreFinalFields(fields);

            for (Field field : fieldsList) {
                field.setAccessible(true);

                Map<AnnotationsEnum, Annotation> map = getAnnotationsMap(field);
                Object result = getRandomForType(field, map);

                field.set(type, result);
            }

            return type;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private static List<Field> ignoreFinalFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
//                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .collect(Collectors.toList());
    }

    private static HashMap<AnnotationsEnum, Annotation> getAnnotationsMap(Field field) {
        HashMap<AnnotationsEnum, Annotation> hashMap = new HashMap<>();

        for (Annotation annotation : field.getAnnotations()) {
            if (annotation instanceof Pattern) {
                hashMap.put(PATTERN, annotation);
            }
            if (annotation instanceof Size) {
                hashMap.put(SIZE, annotation);
            }
        }

        return hashMap;
    }

    private static boolean isComplexField(Class<?> fieldType) {
        if(nonNull(fieldType.getPackage()))
            return !fieldType.getPackage().getName().startsWith("java");

        return false;
    }

    private static int limitateDefaultMaxValue(Size size) {
        return size.max() == Integer.MAX_VALUE ? size.min() : size.max();
    }

    private static Object getRandomForType(Field field,
                                           Map<AnnotationsEnum, Annotation> hashMap) throws Exception {

        Class<?> fieldType = field.getType();

        if (fieldType == String.class) {
            String string = RandomStringUtils.randomAlphanumeric(10);

            if (hashMap.containsKey(PATTERN)) {
                Pattern pattern = (Pattern) hashMap.get(PATTERN);
                string = new RgxGen(pattern.regexp()).generate();
            }

            if (hashMap.containsKey(SIZE)) {
                Size size = (Size) hashMap.get(SIZE);
                int max = limitateDefaultMaxValue(size);
                string = RandomStringUtils.randomAlphanumeric(size.min(), max);
            }

            return string;
        }

        if (fieldType == Long.class || fieldType == long.class) {
            return r.nextLong();
        }

        if (fieldType == Integer.class || fieldType == int.class) {
            return r.nextInt(10);
        }

        if (fieldType == Double.class || fieldType == double.class) {
            return r.nextDouble();
        }

        if (fieldType == Boolean.class || fieldType == boolean.class) {
            return r.nextBoolean();
        }

        if (fieldType == LocalDateTime.class) {
            return LocalDateTime.now();
        }

        if (fieldType == OffsetDateTime.class) {
            return OffsetDateTime.now();
        }

        if (fieldType == Instant.class) {
            return Instant.now();
        }

        if (fieldType == UUID.class) {
            return UUID.randomUUID();
        }

        //Here we can identify what types are not POJOs
        if (isComplexField(fieldType)) {
            if (fieldType.isEnum()) {
                return fieldType.getEnumConstants()[0];
            } else {
                return generate(fieldType);
            }
        }

        if (fieldType == List.class) {
            //If the field is a List, we need to get the generic type
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Type innerType = parameterizedType.getActualTypeArguments()[0];
            Class<?> innerClass = Class.forName(innerType.getTypeName()); //Fully qualified name

            List<Object> list = new ArrayList<>();
            Object innerClassFixture;

            //Controls how many random items are going to be inside List attribute
            for (int i = 0; i < 1; i++) {
                innerClassFixture = generate(innerClass);
                list.add(innerClassFixture);
            }
            return list;
        }

        if (fieldType == Map.class) {
            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Type innerTypeKey = parameterizedType.getActualTypeArguments()[0];
            Type innerTypeValue = parameterizedType.getActualTypeArguments()[1];

            Class<?> innerClassKey = Class.forName(innerTypeKey.getTypeName());
            Class<?> innerClassValue = Class.forName(innerTypeValue.getTypeName());

            Map<Object, Object> map = new HashMap<>();

            map.put(generate(innerClassKey), generate(innerClassValue));
            return map;
        }

        throw new Exception("Type not recognized: ".concat(fieldType.getTypeName()));
    }
}
