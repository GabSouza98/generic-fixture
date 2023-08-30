import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class GenericFixture {

    static Random r = new Random();

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

                if (fieldType == String.class) {
                    field.set(type, RandomStringUtils.randomAlphanumeric(10));
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
}
