package math;

import java.util.function.Supplier;

public class Log2Example {
    private static final double log2div = 1.000000000001 / Math.log(2);

    public static void main(String[] args) {
        int integer = pow(2, 30);

        timer("log2   ", () -> log2(integer));
        timer("binlog ", () -> binlog(integer));
        timer("log2fp0", () -> log2fp0(integer));
        timer("log2nlz", () -> log2nlz(integer));

        test(2, 30);
    }

    private static void test(int base, int pow) {
        int x = pow(base, pow);
        if (pow != log(x, base))
            System.out.printf("error at %d^%d%n", base, pow);
        if(pow != 0 && (pow - 1) != log(x - 1, base))
            System.out.printf("error at %d^%d-1%n", base, pow);
    }

    public static int log(int x, int base) {
        return (int) (Math.log(x) / Math.log(base));
    }

    public static int binlog(int bits) // returns 0 for bits=0
    {
        int log = 0;
        if ((bits & 0xffff0000) != 0) {
            bits >>>= 16;
            log = 16;
        }
        if (bits >= 256) {
            bits >>>= 8;
            log += 8;
        }
        if (bits >= 16) {
            bits >>>= 4;
            log += 4;
        }
        if (bits >= 4) {
            bits >>>= 2;
            log += 2;
        }
        return log + (bits >>> 1);
    }

    public static int log2fp0(int bits) {
        if (bits == 0)
            return 0; // or throw exception
        return (int) (Math.log(bits & 0xffffffffL) * log2div);
    }

    public static int log2(int n) {
        return (int) (Math.log(n) / Math.log(2));
    }

    public static int log2nlz(int bits) {
        if (bits == 0)
            return 0; // or throw exception
        return 31 - Integer.numberOfLeadingZeros(bits);
    }

    private static int pow(int base, int power) {
        int result = 1;
        for (int i = 0; i < power; i++)
            result *= base;
        return result;
    }

    private static void timer(String methodName, Supplier<?> action) {
        long start = System.nanoTime();
        Object result = action.get();
        long end = System.nanoTime();
        System.out.printf("%s: %s %s ms %n", methodName, result, end - start);
    }

}
