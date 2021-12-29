package thread.ex1;

public class IntegerUtils {
    public static Integer countDigits(Integer number) {
        if (number < 100_000) {
            if (number < 100) {
                return lessThan(number, 10, 1, 2);
            } else {
                if (number < 1_000) {
                    return 3;
                } else {
                    return lessThan(number, 10_000, 4, 5);
                }
            }
        } else {
            if (number < 10_000_000) {
                return lessThan(number, 1_000_000, 6, 7);
            } else {
                if (number < 100_000_000) {
                    return 8;
                } else {
                    return lessThan(number, 1_000_000_000, 9, 10);
                }
            }
        }
    }

    public static int lessThan(final Integer number,
                               final int limit,
                               final int toReturnIfLess,
                               final int toReturnIfMoreOrEqual) {
        if (number < limit) {
            return toReturnIfLess;
        } else {
            return toReturnIfMoreOrEqual;
        }
    }
}
