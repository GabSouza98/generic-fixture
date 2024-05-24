package utils;

import enums.AnnotationsEnum;
import exceptions.AnnotationNotImplementedYet;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Map;

import static enums.AnnotationsEnum.NEGATIVE_OR_ZERO;

public class UtilsBigDecimal {

    static SecureRandom RANDOM = new SecureRandom();

    public static BigDecimal returnValueByPattern(Map<AnnotationsEnum, Annotation> hashMap) {
        if (hashMap.containsKey(AnnotationsEnum.DIGITS)) {
            Digits digits = (Digits) hashMap.get(AnnotationsEnum.DIGITS);
            return new BigDecimal(String.format("%s.%s", getRandomDigits(digits.integer()), getRandomDigits(digits.fraction())));
        }

        if (hashMap.containsKey(AnnotationsEnum.POSITIVE) || hashMap.containsKey(AnnotationsEnum.POSITIVE_OR_ZERO)) {
            return new BigDecimal(String.format("%d", 1 + getRandomBetweenOneAndTen()));
        }

        if (hashMap.containsKey(AnnotationsEnum.NEGATIVE) || hashMap.containsKey(NEGATIVE_OR_ZERO)) {
            return new BigDecimal(String.format("%d", returnNegativeValue()));
        }

        if (hashMap.containsKey(AnnotationsEnum.DECIMAL_MIN)) {
            DecimalMin decimalMin = (DecimalMin) hashMap.get(AnnotationsEnum.DECIMAL_MIN);
            return new BigDecimal(String.format("%d.%s", getRandomBetweenOneAndTen(), getRandomDigits(Integer.parseInt(decimalMin.value()))));
        }

        if (hashMap.containsKey(AnnotationsEnum.DECIMAL_MAX)) {
            DecimalMax decimalMax = (DecimalMax) hashMap.get(AnnotationsEnum.DECIMAL_MAX);
            return new BigDecimal(String.format("%S", getRandomDigits(Integer.parseInt(decimalMax.value()))));
        }

        if (hashMap.containsKey(AnnotationsEnum.MIN)) {
            Min min = (Min) hashMap.get(AnnotationsEnum.MIN);
            return new BigDecimal(String.format("%S", RANDOM.longs(min.value(), min.value() + 10).findFirst().getAsLong()));
        }

        if (hashMap.containsKey(AnnotationsEnum.MAX)) {
            Max max = (Max) hashMap.get(AnnotationsEnum.MAX);
            return new BigDecimal(String.format("%S", RANDOM.longs(max.value() -10, max.value()).findFirst().getAsLong()));
        }

        throw new AnnotationNotImplementedYet(hashMap.toString());

    }

    private static int getRandomBetweenOneAndTen() {
        return RANDOM.ints(1, 10).findFirst().getAsInt();
    }

    private static int returnNegativeValue() {
        return (RANDOM.nextInt(10) + 1) * - 1;
    }


    private static String getRandomDigits(int digits) {
        String value = String.valueOf(getRandomBetweenOneAndTen());
        for (int i = 1; i < digits; i++) {
            value = value.concat(String.valueOf(getRandomBetweenOneAndTen()));
        }

        return value;

    }

    private static String getRandomDigits(long digits ) {
        String value = String.valueOf(getRandomBetweenOneAndTen());
        for (int i = 1; i < digits; i++) {
            value = value.concat(String.valueOf(getRandomBetweenOneAndTen()));
        }

        return value;

    }

}
