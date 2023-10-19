import com.github.curiousoddman.rgxgen.RgxGen;
import domain.UpdateIgnoreFields;
import enums.AnnotationsEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
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

    public static <T> T generate(Class<T> clazz, List<String> ignoredFields) throws Exception {

        try {

            if (!hasNoArgsConstructor(clazz)) {
                throw new Exception("A classe precisa ter um construtor vazio");
            }

            T type = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = type.getClass().getDeclaredFields();
            List<Field> fieldsList = ignoreFinalFields(fields, ignoredFields);
            ignoredFields = UpdateIgnoreFields.update(ignoredFields);

            for (Field field : fieldsList) {
                field.setAccessible(true);

                Map<AnnotationsEnum, Annotation> map = getAnnotationsMap(field);
                Object result = getRandomForType(field.getType(), field.getGenericType(), map, ignoredFields);

                field.set(type, result);
            }

            return type;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

//    public static <T> T generate(Class<T> clazz) throws Exception {
//
//        try {
//
//            if (!hasNoArgsConstructor(clazz)) {
//                throw new Exception("A classe precisa ter um construtor vazio");
//            }
//
//            T type = clazz.getDeclaredConstructor().newInstance();
//            Field[] fields = type.getClass().getDeclaredFields();
//            List<Field> fieldsList = ignoreFinalFields(fields);
//
//            for (Field field : fieldsList) {
//                field.setAccessible(true);
//
//                Map<AnnotationsEnum, Annotation> map = getAnnotationsMap(field);
//                Object result = getRandomForType(field.getType(), field.getGenericType(), map, null);
//
//                field.set(type, result);
//            }
//
//            return type;
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            throw e;
//        }
//    }

    private static List<Field> ignoreFinalFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
//                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .collect(Collectors.toList());
    }
    private static List<Field> ignoreFinalFields(Field[] fields, List<String> ignoredFields) {
        return Arrays.stream(fields)
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
//                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> !ignoredFields.contains(f.getName()))
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

    private static int limitateDefaultMaxValue(Size size) {
        return size.max() == Integer.MAX_VALUE ? size.min() : size.max();
    }

    private static Object getRandomForType(Class<?> fieldType,
                                           Type type,
                                           Map<AnnotationsEnum, Annotation> hashMap,
                                           List<String> ignoredFields) throws Exception {

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

        if (fieldType == Character.class || fieldType == char.class) {
            return RandomStringUtils.randomAlphabetic(1).charAt(0);
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
        if (isComplexClass(fieldType)) {
            if (fieldType.isEnum()) {
                return fieldType.getEnumConstants()[0];
            } else {
                return generate(fieldType, ignoredFields);
            }
        }

        if (fieldType == List.class) {
            Class<?>[] innerClasses = getInnerClasses(type);
            List<Object> list = new ArrayList<>();

            Object obj = getObjectByClass(innerClasses[0], ignoredFields);

            for (int i = 0; i < 1; i++) {
                list.add(obj);
            }
            return list;
        }

        if (fieldType == Map.class) {
            Class<?>[] innerClasses = getInnerClasses(type);
            Map<Object, Object> map = new HashMap<>();

            Object key = getObjectByClass(innerClasses[0], ignoredFields);
            Object value = getObjectByClass(innerClasses[1], ignoredFields);

            map.put(key, value);
            return map;
        }

        throw new Exception("Type not recognized: ".concat(fieldType.getTypeName()));
    }

    private static boolean hasNoArgsConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        for(Constructor<?> c: constructors) {
            if (c.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    private static Object getObjectByClass(Class<?> innerClass, List<String> ignoredFields) throws Exception {
        return isComplexClass(innerClass) ?
                generate(innerClass, ignoredFields) :
                getRandomForType(innerClass, null, new HashMap<>(), null);
    }

    private static boolean isComplexClass(Class<?> clazz) {
        if(nonNull(clazz.getPackage())) {
            return !clazz.getPackage().getName().startsWith("java");
        }

        return false;
    }

    private static Class<?>[] getInnerClasses(Type type) throws ClassNotFoundException {
        //If the field has generics, we need to get the generic types
        ParameterizedType parameterizedType = (ParameterizedType) type;
        //Generic types, like List<T> or Map<K, V>
        Type[] types = parameterizedType.getActualTypeArguments();

        Class<?>[] innerClasses = new Class<?>[types.length];
        for (int i=0; i<types.length; i++) {
            Type t = types[i];
            Class<?> innerClass = Class.forName(t.getTypeName()); //Fully qualified name
            innerClasses[i] = innerClass;
        }

        return innerClasses;
    }
}
