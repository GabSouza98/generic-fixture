import com.github.curiousoddman.rgxgen.RgxGen;
import enums.AnnotationsEnum;
import exceptions.NoArgsConstructorException;
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
import static utils.UpdateIgnoredFields.removeItem;

public class GenericFixture {

    static SecureRandom r = new SecureRandom();

    public static <T> T generate(Class<T> clazz) throws Exception {
        return doGenerate(clazz, new ArrayList<>(), "");
    }

    public static <T> T generate(Class<T> clazz, List<String> ignoredFields) throws Exception {
        return doGenerate(clazz, ignoredFields, "");
    }

    private static <T> T generate(Class<T> clazz, List<String> ignoredFields, String attributesPath) throws Exception {
        return doGenerate(clazz, ignoredFields, attributesPath);
    }

    public static <T> T doGenerate(Class<T> clazz, List<String> ignoredFields, String attributesPath) throws Exception {

        try {

            if (!hasNoArgsConstructor(clazz)) {
                throw new NoArgsConstructorException();
            }

            T type = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = type.getClass().getDeclaredFields();
            List<Field> fieldsList = ignoreFinalFields(fields);

            for (Field field : fieldsList) {
                field.setAccessible(true);

                String fieldName = field.getName();

                String currentPath = handleAttributesPath(fieldName, attributesPath);

                if (!ignoredFields.isEmpty() && isIgnorableField(ignoredFields, currentPath)) {
                    //to no lugar certo
                    //field.set(type, personalFields.get(currentPath));
                    ignoredFields = removeItem(ignoredFields, currentPath); //This line is optional
                    continue;
                }

                Map<AnnotationsEnum, Annotation> map = getAnnotationsMap(field);
                Object result = getRandomForType(field.getType(), field.getGenericType(), map, ignoredFields, currentPath);

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
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .collect(Collectors.toList());
    }

    private static boolean isIgnorableField(List<String> ignoredFields, String currentPath) {
        //If ignoredFields contains "A.B.C" and currentPath is exactly "A.B.C"
        return ignoredFields.stream().anyMatch(f -> f.equals(currentPath));
    }

    public static String handleAttributesPath(String fieldName, String attributesPath) {
        if (attributesPath.isEmpty()) {
            //First iteration, no recursion
            return fieldName;
        } else {
            return attributesPath.concat(".").concat(fieldName);
        }
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
                                           List<String> ignoredFields,
                                           String currentPath) throws Exception {

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
            return r.nextInt(100000);
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
                return generate(fieldType, ignoredFields, currentPath);
            }
        }

        if (implementsCollection(fieldType)) {

            Class<?>[] innerClasses = getInnerClasses(type); //Get the Generic type inside List<T>
            Object obj = getObjectByClass(innerClasses[0], ignoredFields, currentPath);
            Collection<Object> collection = null;

            if (fieldType.isInterface()) {

                //Verify all possible Interfaces that extends Collection
                //and choose default implementation
                if (fieldType == List.class) {
                    collection = new ArrayList<>();
                }
                if (fieldType == Queue.class) {
                    collection = new PriorityQueue<>();
                }
                if (fieldType == Deque.class) {
                    collection = new ArrayDeque<>();
                }
                if (fieldType == Set.class) {
                    collection = new HashSet<>();
                }
                if (fieldType == SortedSet.class) {
                    collection = new TreeSet<>();
                }

            } else {
                collection = (Collection<Object>) fieldType.getDeclaredConstructor().newInstance();
            }

            assert collection != null;

            for (int i = 0; i < 1; i++) {
                collection.add(obj);
            }

            return collection;
        }

        if (implementsMap(fieldType) || isDictionary(fieldType)) {

            Class<?>[] innerClasses = getInnerClasses(type); //Get the Generic type inside List<T>
            Map<Object, Object> map = null;

            Object key = getObjectByClass(innerClasses[0], ignoredFields, currentPath);
            Object value = getObjectByClass(innerClasses[1], ignoredFields, currentPath);

            if (fieldType.isInterface() || Modifier.isAbstract(fieldType.getModifiers())) {

                //Verify all possible Interfaces/AbstractClasses that extends Map
                //and choose default implementation
                if (fieldType == Map.class ||
                    fieldType == AbstractMap.class ||
                    fieldType == SortedMap.class ||
                    fieldType == NavigableMap.class) {
                    map = new TreeMap<>();
                }

                if (fieldType == Dictionary.class) {
                    map = new Hashtable<>();
                }

            } else {
                map = (Map<Object, Object>) fieldType.getDeclaredConstructor().newInstance();
            }

            assert map != null;

            for (int i = 0; i < 1; i++) {
                map.put(key, value);
            }

            return map;
        }

        if (fieldType == Map.class) {

            Class<?>[] innerClasses = getInnerClasses(type);
            Map<Object, Object> map = new HashMap<>();

            Object key = getObjectByClass(innerClasses[0], ignoredFields, currentPath);
            Object value = getObjectByClass(innerClasses[1], ignoredFields, currentPath);

            map.put(key, value);
            return map;
        }

        throw new Exception("Type not recognized: ".concat(fieldType.getTypeName()));
    }

    private static boolean implementsCollection(Class<?> fieldType) {
        return Collection.class.isAssignableFrom(fieldType);
    }

    private static boolean implementsMap(Class<?> fieldType) {
        return Map.class.isAssignableFrom(fieldType);
    }

    private static boolean isDictionary(Class<?> fieldType) {
        return fieldType == Dictionary.class;
    }

    private static boolean hasNoArgsConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> c : constructors) {
            if (c.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    private static Object getObjectByClass(Class<?> innerClass, List<String> ignoredFields, String currentPath) throws Exception {
        return isComplexClass(innerClass) ?
                generate(innerClass, ignoredFields, currentPath) :
                getRandomForType(innerClass, null, new HashMap<>(), null, currentPath);
    }

    private static boolean isComplexClass(Class<?> clazz) {
        if (nonNull(clazz.getPackage())) {
            return !clazz.getPackage().getName().startsWith("java");
        }

        return false;
    }

    private static Class<?>[] getInnerClasses(Type type) throws ClassNotFoundException {
        //If the field has generics, we need to get the generic types
        ParameterizedType parameterizedType = (ParameterizedType) type;
        //Inner types, like T of List<T> or K, V of Map<K, V>
        Type[] types = parameterizedType.getActualTypeArguments();

        Class<?>[] innerClasses = new Class<?>[types.length];
        for (int i = 0; i < types.length; i++) {
            Type t = types[i];
            Class<?> innerClass = Class.forName(t.getTypeName()); //Fully qualified name
            innerClasses[i] = innerClass;
        }

        return innerClasses;
    }
}
