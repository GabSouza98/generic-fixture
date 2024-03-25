package enums;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum AnnotationsEnum {
    PATTERN,
    SIZE,
    PAST,
    PAST_OR_PRESENT,
    DIGITS,
    POSITIVE,
    POSITIVE_OR_ZERO,
    NEGATIVE,
    NEGATIVE_OR_ZERO,
    FUTURE,
    FUTURE_OR_PRESENT,
    DECIMAL_MIN,
    DECIMAL_MAX,
    MIN,
    MAX,
    EMAIL;
}
