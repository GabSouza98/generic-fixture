import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenericFixture {

    static Random r = new Random();

    public static <T> T generate(Class<T> generic) throws Exception {

        try {

            T type = generic.getDeclaredConstructor().newInstance();

            Field[] fields = type.getClass().getDeclaredFields();

            for (Field field : fields) {

                field.setAccessible(true);

                Class<?> fieldType = field.getType();

                if (fieldType == String.class) {
                    field.set(type, RandomStringUtils.randomAlphanumeric(10));
                }

                if (fieldType == Long.class || fieldType == long.class) {
                    field.set(type, r.nextLong(10));
                }

                if (fieldType == Integer.class || fieldType == int.class) {
                    field.set(type, r.nextInt(10));
                }

                if (fieldType == Double.class || fieldType == double.class) {
                    field.set(type, r.nextDouble(10));
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

                if (!fieldType.getPackageName().startsWith("java")) {
                    //Here we can identify what types are not POJOs
                    field.set(type, generate(fieldType));
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
                        if (innerClass.getPackageName().startsWith("java")) {
                            innerClassFixture = getRandomForType(innerClass);
                        } else {
                            innerClassFixture = generate(innerClass);
                        }
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

    private static Object getRandomForType(Class<?> innerClass) throws Exception {

        if (innerClass == String.class) {
            return RandomStringUtils.randomAlphanumeric(10);
        }

        if (innerClass == Long.class || innerClass == long.class) {
            return r.nextLong(10);
        }

        if (innerClass == Integer.class || innerClass == int.class) {
            return r.nextInt(10);
        }

        if (innerClass == Double.class || innerClass == double.class) {
            return r.nextDouble(10);
        }

        if (innerClass == Boolean.class || innerClass == boolean.class) {
            return r.nextBoolean();
        }

        if (innerClass == LocalDateTime.class) {
            return LocalDateTime.now();
        }

        if (innerClass == OffsetDateTime.class) {
            return OffsetDateTime.now();
        }

        throw new Exception("Unrecongnized type");

    }


}
