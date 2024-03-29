package utils;

@FunctionalInterface
public interface TriFunction<Arg1, Arg2, Arg3, Out> {
    Out apply(Arg1 arg1, Arg2 arg2, Arg3 arg3);
}
