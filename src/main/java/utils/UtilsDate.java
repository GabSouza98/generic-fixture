package utils;

import enums.AnnotationsEnum;
import exceptions.AnnotationNotImplementedYet;
import exceptions.ClassNotImplementedYet;

import java.lang.annotation.Annotation;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static enums.AnnotationsEnum.FUTURE;
import static enums.AnnotationsEnum.FUTURE_OR_PRESENT;
import static enums.AnnotationsEnum.PAST;
import static enums.AnnotationsEnum.PAST_OR_PRESENT;
import static utils.UtilsAnnotations.hasAnnotation;

public class UtilsDate {
    public static Object returnValueByPatternAndType(Map<AnnotationsEnum, Annotation> hashMap, Object time) {
        if(hasAnnotation(hashMap)) {
            if (hashMap.containsKey(PAST) || hashMap.containsKey(PAST_OR_PRESENT)) {
                return returnPastDate(time);
            }
            if (hashMap.containsKey(FUTURE) || hashMap.containsKey(FUTURE_OR_PRESENT)) {
                return returnFutureDate(time);
            }
        }

        throw new AnnotationNotImplementedYet(hashMap.toString());
    }

    private static Object returnFutureDate(Object time) {
        if (time == LocalDateTime.class) {
            return LocalDateTime.now().plusDays(1);
        }

        if (time == OffsetDateTime.class) {
            return OffsetDateTime.now().plusDays(1);
        }

        if (time == Instant.class) {
            return Instant.now().plus(1, ChronoUnit.DAYS);
        }

        if (time == ZonedDateTime.class) {
            return ZonedDateTime.now().plusDays(1);
        }

        if (time == LocalDate.class) {
            return LocalDate.now().plusDays(1);
        }

        if (time == LocalTime.class) {
            return LocalTime.now().plusSeconds(1);
        }

        if (time == OffsetTime.class) {
            return OffsetTime.now().plusSeconds(1);
        }

        if (time == Date.class) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            return calendar.getTime();
        }

        throw new ClassNotImplementedYet(time);

    }

    private static Object returnPastDate(Object time) {
        if (time == LocalDateTime.class) {
            return LocalDateTime.now().minusDays(1);
        }

        if (time == OffsetDateTime.class) {
            return OffsetDateTime.now().minusDays(1);
        }

        if (time == Instant.class) {
            return Instant.now().minus(1, ChronoUnit.DAYS);
        }

        if (time == ZonedDateTime.class) {
            return ZonedDateTime.now().minusDays(1);
        }

        if (time == LocalDate.class) {
            return LocalDate.now().minusDays(1);
        }

        if (time == LocalTime.class) {
            return LocalTime.now().minusSeconds(1);
        }

        if (time == OffsetTime.class) {
            return OffsetTime.now().minusSeconds(1);
        }

        if (time == Date.class) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            return calendar.getTime();
        }

        throw new ClassNotImplementedYet(time);
    }

}
