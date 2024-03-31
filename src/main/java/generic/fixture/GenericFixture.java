package generic.fixture;

import com.github.curiousoddman.rgxgen.RgxGen;
import enums.AnnotationsEnum;
import exceptions.TypeNotRecognizedException;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Negative;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.temporal.Temporal;
import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Deque;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import static enums.AnnotationsEnum.DECIMAL_MAX;
import static enums.AnnotationsEnum.DECIMAL_MIN;
import static enums.AnnotationsEnum.DIGITS;
import static enums.AnnotationsEnum.EMAIL;
import static enums.AnnotationsEnum.FUTURE;
import static enums.AnnotationsEnum.FUTURE_OR_PRESENT;
import static enums.AnnotationsEnum.MAX;
import static enums.AnnotationsEnum.MIN;
import static enums.AnnotationsEnum.NEGATIVE;
import static enums.AnnotationsEnum.NEGATIVE_OR_ZERO;
import static enums.AnnotationsEnum.PAST;
import static enums.AnnotationsEnum.PAST_OR_PRESENT;
import static enums.AnnotationsEnum.PATTERN;
import static enums.AnnotationsEnum.POSITIVE;
import static enums.AnnotationsEnum.POSITIVE_OR_ZERO;
import static enums.AnnotationsEnum.SIZE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static utils.UtilsAnnotations.hasAnnotation;
import static utils.UtilsAnnotations.limitateDefaultMaxValue;
import static utils.UtilsBigDecimal.returnValueByPattern;
import static utils.UtilsDate.returnValueByPatternAndType;

public class GenericFixture {
    static SecureRandom random = new SecureRandom();

    public static <T> T generate(Class<T> clazz) {
        return doGenerate(clazz, new HashMap<>(), "", 1, new HashSet<Class<?>>());
    }

    public static <T> T generate(Class<T> clazz, Map<String, Object> customFields) {
        return doGenerate(clazz, customFields, "", 1, new HashSet<Class<?>>());
    }

    public static <T> T generate(Class<T> clazz, Integer numberOfItems) {
        return doGenerate(clazz, new HashMap<>(), "", numberOfItems, new HashSet<Class<?>>());
    }

    public static <T> T generate(Class<T> clazz, Map<String, Object> customFields, Integer numberOfItems) {
        return doGenerate(clazz, customFields, "", numberOfItems, new HashSet<Class<?>>());
    }

    private static <T> T generate(Class<T> clazz, Map<String, Object> customFields, String attributesPath, Integer numberOfItems, Set<Class<?>> visitedClasses) {
        return doGenerate(clazz, customFields, attributesPath, numberOfItems, visitedClasses);
    }

    private static <T> T doGenerate(Class<T> clazz, Map<String, Object> customFields, String attributesPath, Integer numberOfItems, Set<Class<?>> visitedClass) {

        try {

            if(isComplexClass(clazz)) {
                visitedClass.add(clazz);
            }

            T type;

            if (hasNoArgsConstructor(clazz)) {
                type = clazz.getDeclaredConstructor().newInstance();
            } else {
                type = getInstanceForConstructorWithLessArguments(clazz, numberOfItems, visitedClass);
            }

            Field[] fields = type.getClass().getDeclaredFields();
            List<Field> fieldsList = ignoreFinalFields(fields);

            for (Field field : fieldsList) {
                //Avoid circular attribute generation
                if(visitedClass.contains(field.getType())) {
                    continue;
                }

                field.setAccessible(true);

                String fieldName = field.getName();

                String currentPath = handleAttributesPath(fieldName, attributesPath);

                if (nonNull(customFields) && !customFields.isEmpty() && isCustomField(customFields, currentPath)) {
                    field.set(type, customFields.get(currentPath));
                    customFields.remove(currentPath); //This line is optional
                    continue;
                }
                //Only set field value if not already defined.
                if (isNull(field.get(type)) || field.getType().isPrimitive()) {
                    Map<AnnotationsEnum, Annotation> map = getAnnotationsMap(field);
                    Object result = getRandomForType(field.getType(), field.getGenericType(), map, customFields, currentPath, numberOfItems, visitedClass);
                    field.set(type, result);
                }
            }
            visitedClass.remove(clazz); //Able GenericFixture to generate another attributes for this clazz, because the circular generation will not happen.
            return type;

        } catch (Exception e) {
            System.out.println("\nError ocurred ".concat(e.toString()));
            throw new RuntimeException(e.getMessage());
        }
    }

    private static List<Field> ignoreFinalFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(f -> !Modifier.isFinal(f.getModifiers()))
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .collect(Collectors.toList());
    }

    private static <T> T getInstanceForConstructorWithLessArguments(Class<?> clazz, Integer numberOfItems, Set<Class<?>> visitedClass) throws Exception {

        Constructor<?>[] constructors = clazz.getConstructors();

        //Order constructor array by lesser parameter count
        Object[] ordenedConstructors = Arrays.stream(constructors)
                .sorted(Comparator.comparing(Constructor::getParameterCount))
                .toArray();

        Constructor<?> construtor = (Constructor<?>) ordenedConstructors[0];

        //Get array of parameter types of the constructor
        Class<?>[] parameterTypes = construtor.getParameterTypes();

        //Array for storing the values for each argument
        Object[] arguments = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {

            if (parameterTypes[i].isPrimitive()) {
                //Generates a value for each primitive argument
                arguments[i] = getRandomForType(parameterTypes[i], parameterTypes[i], new HashMap<>(), new HashMap<>(), "", numberOfItems, visitedClass);
            } else {
                arguments[i] = null;
            }

        }

        return (T) construtor.newInstance(arguments);
    }

    private static boolean isCustomField(Map<String, Object> customFields, String currentPath) {
        //If customFields contains "A.B.C" and currentPath is exactly "A.B.C"
        return customFields.keySet().stream().anyMatch(f -> f.equals(currentPath));
    }

    private static String handleAttributesPath(String fieldName, String attributesPath) {
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

            if (annotation instanceof Past) {
                hashMap.put(PAST, annotation);
            }

            if (annotation instanceof PastOrPresent) {
                hashMap.put(PAST_OR_PRESENT, annotation);
            }

            if (annotation instanceof Digits) {
                hashMap.put(DIGITS, annotation);
            }

            if (annotation instanceof Positive) {
                hashMap.put(POSITIVE, annotation);
            }

            if (annotation instanceof PositiveOrZero) {
                hashMap.put(POSITIVE_OR_ZERO, annotation);
            }

            if (annotation instanceof Negative) {
                hashMap.put(NEGATIVE, annotation);
            }

            if (annotation instanceof NegativeOrZero) {
                hashMap.put(NEGATIVE_OR_ZERO, annotation);
            }

            if (annotation instanceof Future) {
                hashMap.put(FUTURE, annotation);
            }

            if (annotation instanceof FutureOrPresent) {
                hashMap.put(FUTURE_OR_PRESENT, annotation);
            }

            if (annotation instanceof Email) {
                hashMap.put(EMAIL, annotation);
            }

            if (annotation instanceof DecimalMin) {
                hashMap.put(DECIMAL_MIN, annotation);
            }

            if (annotation instanceof DecimalMax) {
                hashMap.put(DECIMAL_MAX, annotation);
            }

            if (annotation instanceof Min) {
                hashMap.put(MIN, annotation);
            }

            if (annotation instanceof Max) {
                hashMap.put(MAX, annotation);
            }
        }

        return hashMap;
    }

    private static Object getRandomForType(Class<?> fieldType,
                                           Type type,
                                           Map<AnnotationsEnum, Annotation> hashMap,
                                           Map<String, Object> customFields,
                                           String currentPath,
                                           Integer numberOfItems,
                                           Set<Class<?>> visitedClass) throws Exception {

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

            if (hashMap.containsKey(PAST)
                    || hashMap.containsKey(PAST_OR_PRESENT)
                    || hashMap.containsKey(FUTURE)
                    || hashMap.containsKey(FUTURE_OR_PRESENT)) {
                string = returnValueByPatternAndType(hashMap, LocalDateTime.class).toString();
            }

            if (hashMap.containsKey(EMAIL)) {
                string = new RgxGen("^[a-zA-Z0-9_.]{1,10}@email\\.com$").generate();
            }

            if (hashMap.containsKey(POSITIVE)
                || hashMap.containsKey(POSITIVE_OR_ZERO)
                || hashMap.containsKey(NEGATIVE)
                || hashMap.containsKey(NEGATIVE_OR_ZERO)
                || hashMap.containsKey(DECIMAL_MIN)
                || hashMap.containsKey(DECIMAL_MAX)
                || hashMap.containsKey(DIGITS)
                || hashMap.containsKey(MIN)
                || hashMap.containsKey(MAX)) {
                string = returnValueByPattern(hashMap).toString();
            }

            return string;
        }

        if (fieldType == Long.class || fieldType == long.class) {
            if (hashMap.containsKey(DIGITS)
                || hashMap.containsKey(POSITIVE)
                || hashMap.containsKey(POSITIVE_OR_ZERO)
                || hashMap.containsKey(NEGATIVE)
                || hashMap.containsKey(NEGATIVE_OR_ZERO)
                || hashMap.containsKey(DECIMAL_MAX)
                || hashMap.containsKey(MIN)
                || hashMap.containsKey(MAX)) {
                //The transformation to long discards decimal places
                return returnValueByPattern(hashMap).longValue();
            }

            return random.nextLong();
        }

        if (fieldType == Integer.class || fieldType == int.class) {
            if (hashMap.containsKey(DIGITS)
                || hashMap.containsKey(POSITIVE)
                || hashMap.containsKey(POSITIVE_OR_ZERO)
                || hashMap.containsKey(NEGATIVE)
                || hashMap.containsKey(NEGATIVE_OR_ZERO)
                || hashMap.containsKey(DECIMAL_MAX)
                || hashMap.containsKey(MIN)
                || hashMap.containsKey(MAX)) {
                return returnValueByPattern(hashMap).intValue();
            }

            return random.nextInt(100000);
        }

        if (fieldType == Double.class || fieldType == double.class) {
            if (hashMap.containsKey(DIGITS)
                || hashMap.containsKey(POSITIVE)
                || hashMap.containsKey(POSITIVE_OR_ZERO)
                || hashMap.containsKey(NEGATIVE)
                || hashMap.containsKey(NEGATIVE_OR_ZERO)
                || hashMap.containsKey(DECIMAL_MIN)
                || hashMap.containsKey(DECIMAL_MAX)
                || hashMap.containsKey(MIN)
                || hashMap.containsKey(MAX)) {
                return returnValueByPattern(hashMap).doubleValue();
            }

            return random.nextDouble();
        }

        if (fieldType == Boolean.class || fieldType == boolean.class) {
            return random.nextBoolean();
        }

        if (fieldType == Character.class || fieldType == char.class) {
            return randomAlphabetic(1).charAt(0);
        }

        if (fieldType == BigDecimal.class) {
            if (hasAnnotation(hashMap)) {
               return returnValueByPattern(hashMap);
            }
            return BigDecimal.valueOf(random.nextDouble());
        }

        if (fieldType == Date.class ) {
            if(hasAnnotation(hashMap)) {
                return returnValueByPatternAndType(hashMap, Date.class);
            }
            return new Date();
        }

        if (implementsTemporal(fieldType)) {
            if (fieldType.isInterface()) {

                if (fieldType == ChronoLocalDate.class) {
                    return LocalDate.now();
                }
                if (fieldType == ChronoLocalDateTime.class) {
                    return LocalDateTime.now();
                }
                if (fieldType == ChronoZonedDateTime.class) {
                    return ZonedDateTime.now();
                }

            } else {

                if (fieldType == LocalDateTime.class) {
                    if(hasAnnotation(hashMap)) {
                        return returnValueByPatternAndType(hashMap, LocalDateTime.class);
                    }
                    return LocalDateTime.now();
                }

                if (fieldType == OffsetDateTime.class) {
                    if(hasAnnotation(hashMap)) {
                        return returnValueByPatternAndType(hashMap, OffsetDateTime.class);
                    }
                    return OffsetDateTime.now();
                }

                if (fieldType == Instant.class) {
                    if(hasAnnotation(hashMap)) {
                        return returnValueByPatternAndType(hashMap, Instant.class);
                    }
                    return Instant.now();
                }

                if (fieldType == ZonedDateTime.class) {
                    if(hasAnnotation(hashMap)) {
                        return returnValueByPatternAndType(hashMap, ZonedDateTime.class);
                    }
                    return ZonedDateTime.now();
                }

                if (fieldType == LocalDate.class) {
                    if(hasAnnotation(hashMap)) {
                        return returnValueByPatternAndType(hashMap, LocalDate.class);
                    }
                    return LocalDate.now();
                }

                if (fieldType == LocalTime.class) {
                    if(hasAnnotation(hashMap)) {
                        return returnValueByPatternAndType(hashMap, LocalTime.class);
                    }
                    return LocalTime.now();
                }

                if (fieldType == OffsetTime.class) {
                    if(hasAnnotation(hashMap)) {
                        return returnValueByPatternAndType(hashMap, OffsetTime.class);
                    }
                    return OffsetTime.now();
                }

            }
        }

        if (fieldType == UUID.class) {
            return UUID.randomUUID();
        }

        if (fieldType.isEnum()) {
            return fieldType.getEnumConstants()[0];
        }

        //Here we can identify what types are not POJOs
        if (isComplexClass(fieldType)) {
            return generate(fieldType, customFields, currentPath, numberOfItems, visitedClass);
        }

        if (implementsCollection(fieldType)) {

            Class<?>[] innerClasses = getInnerClasses(type); //Get the Generic type inside List<T>
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

            for (int i = 0; i < numberOfItems; i++) {
                Object obj = getObjectByClass(innerClasses[0], customFields, currentPath, numberOfItems, visitedClass);
                collection.add(obj);
            }

            return collection;
        }

        if (implementsMap(fieldType) || isDictionary(fieldType)) {

            Class<?>[] innerClasses = getInnerClasses(type); //Get the Generic type inside Map<K, V>
            Map<Object, Object> map = null;

            if (fieldType.isInterface() || Modifier.isAbstract(fieldType.getModifiers())) {

                //Verify all possible Interfaces/AbstractClasses that extends Map
                //and choose default implementation
                if (fieldType == Map.class || fieldType == AbstractMap.class) {
                    map = new HashMap<>();
                }

                if (fieldType == ConcurrentMap.class) {
                    map = new ConcurrentHashMap<>();
                }

                //These types require that all keys inserted must implement the Comparable interface
                if (fieldType == SortedMap.class || fieldType == NavigableMap.class) {
                    map = new TreeMap<>();
                }

                //This type require that all keys inserted must implement the Comparable interface
                if (fieldType == ConcurrentNavigableMap.class) {
                    map = new ConcurrentSkipListMap<>();
                }

                if (fieldType == Dictionary.class) {
                    map = new Hashtable<>();
                }

            } else {
                map = (Map<Object, Object>) fieldType.getDeclaredConstructor().newInstance();
            }

            assert map != null;

            for (int i = 0; i < numberOfItems; i++) {
                Object key = getObjectByClass(innerClasses[0], customFields, currentPath, numberOfItems, visitedClass);
                Object value = getObjectByClass(innerClasses[1], customFields, currentPath, numberOfItems, visitedClass);
                tryPutOnMap(type, map, key, value);
            }

            return map;
        }

        if (fieldType.isArray()) {

            Class<?> arrayType = fieldType.getComponentType();
            Object array = Array.newInstance(arrayType, numberOfItems);

            for (int i = 0; i < numberOfItems; i++) {
                if (hasTypeParameters(arrayType)) {
                    //This cast is necessary to parse Map<K,V>[] to Map<K,V>, or List<E>[] to List<E>
                    Type genericType = (((GenericArrayType) type).getGenericComponentType());
                    Array.set(array, i, getRandomForType(arrayType, genericType, hashMap, customFields, currentPath, numberOfItems, visitedClass));
                } else {
                    Array.set(array, i, getObjectByClass(arrayType, customFields, currentPath, numberOfItems, visitedClass));
                }
            }

            return array;
        }

        throw new TypeNotRecognizedException(fieldType.getTypeName());
    }

    private static boolean hasTypeParameters(Class<?> clazz) {
        return clazz.getTypeParameters().length > 0;
    }

    private static void tryPutOnMap(Type type, Map<Object, Object> map, Object key, Object value) {
        try {
            map.put(key, value);
        } catch (ClassCastException e) {
            System.out.printf("\nIt's necessary to implement Comparable<?> Interface in Key Class of the Map: %s ", type.toString());
            throw new ClassCastException("It's necessary to implement Comparable<?> Interface in Key Class of the Map: ".concat(type.toString()));
        }
    }


    private static boolean implementsCollection(Class<?> fieldType) {
        return Collection.class.isAssignableFrom(fieldType);
    }

    private static boolean implementsTemporal(Class<?> fieldType) {
        return Temporal.class.isAssignableFrom(fieldType);
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

    private static Object getObjectByClass(Class<?> innerClass, Map<String, Object> customFields, String currentPath, Integer numberOfItems, Set<Class<?>> visitedClass) throws Exception {
        return isComplexClass(innerClass) ?
                generate(innerClass, customFields, currentPath, numberOfItems, visitedClass) :
                getRandomForType(innerClass, null, new HashMap<>(), null, currentPath, numberOfItems, visitedClass);
    }

    private static boolean isComplexClass(Class<?> clazz) {
        if (nonNull(clazz.getPackage())) {
            return !clazz.getPackageName().startsWith("java") && !clazz.isEnum();
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
