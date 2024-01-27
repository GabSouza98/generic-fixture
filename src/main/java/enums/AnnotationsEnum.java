package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    EMAIL("^[a-zA-Z0-9_.]{1,10}@email\\.com$");

    private String regex;

}
