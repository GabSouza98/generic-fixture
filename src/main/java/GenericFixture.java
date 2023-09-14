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

            List<Field> fieldsList = Arrays.stream(fields)
                    .filter(f -> !Modifier.isStatic(f.getModifiers()))
                    .filter(f -> !Modifier.isFinal(f.getModifiers()))
                    .collect(Collectors.toList());

            for (Field field : fieldsList) {

                field.setAccessible(true);

                Class<?> fieldType = field.getType();

                HashMap<AnnotationsEnum, Annotation> hashMap = new HashMap<>();

                for (Annotation annotation : field.getAnnotations()) {
                    if (annotation instanceof jakarta.validation.constraints.Pattern) {
                        hashMap.put(PATTERN, annotation);
                    }
                    if (annotation instanceof jakarta.validation.constraints.Size) {
                        hashMap.put(AnnotationsEnum.SIZE, annotation);
                    }
                }

                if (fieldType == String.class) {

                    String s = RandomStringUtils.randomAlphanumeric(10);

                    if (hashMap.containsKey(PATTERN)) {
                        s = new RgxGen(((Pattern) hashMap.get(PATTERN)).regexp()).generate();
                    }

                    if (hashMap.containsKey(SIZE)) {
                        Size size = (Size) hashMap.get(SIZE);
                        int max = limitateDefaultMaxValue(size);
                        s = RandomStringUtils.randomAlphanumeric(size.min(), max);
                    }

                    field.set(type, s);
                }

                if (fieldType == Long.class || fieldType == long.class) {
                    field.set(type, r.nextLong());
                }

                if (fieldType == Integer.class || fieldType == int.class) {
                    field.set(type, r.nextInt(10));
                }

                if (fieldType == Double.class || fieldType == double.class) {
                    field.set(type, r.nextDouble());
                }

                if (fieldType == Boolean.class || fieldType == boolean.class) {
                    field.set(type, r.nextBoolean());
                }

                if (fieldType == LocalDateTime.class) {
                    field.set(type, LocalDateTime.now());
                }

                if (fieldType == OffsetDateTime.class) {
                    field.set(type, OffsetDateTime.now());
                }

                if (fieldType == Instant.class) {
                    field.set(type, Instant.now());
                }

                if (fieldType == UUID.class) {
                    field.set(type, UUID.randomUUID());
                }

                //Here we can identify what types are not POJOs
                if (isComplexField(fieldType)) {

                    if (fieldType.isEnum()) {
                        field.set(type, fieldType.getEnumConstants()[0]);
                    } else {
                        field.set(type, generate(fieldType));
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
                    field.set(type, list);
                }

                if (fieldType == Map.class) {
                    ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                    Type innerTypeKey = parameterizedType.getActualTypeArguments()[0];
                    Type innerTypeValue = parameterizedType.getActualTypeArguments()[1];

                    Class<?> innerClassKey = Class.forName(innerTypeKey.getTypeName());
                    Class<?> innerClassValue = Class.forName(innerTypeValue.getTypeName());

                    Map<Object, Object> map = new HashMap<>();

                    map.put(generate(innerClassKey), generate(innerClassValue));

                    field.set(type, map);

                }

            }

            return type;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    private static boolean isComplexField(Class<?> fieldType) {
        if(nonNull(fieldType.getPackage()))
            return !fieldType.getPackage().getName().startsWith("java");

        return false;
    }

    private static int limitateDefaultMaxValue(Size size) {
        return size.max() == Integer.MAX_VALUE ? size.min() : size.max();
    }
}
