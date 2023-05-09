package core.concurrency.thread_pool.forkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FibonacciExample {

    public static void main(String[] args) {

        final int numberOfProcessors = Runtime.getRuntime().availableProcessors();
        final ForkJoinPool forkJoinPool = new ForkJoinPool(numberOfProcessors);

        final ForkJoinTask<Integer> result = forkJoinPool.submit(new Fibonacci(10));

        System.out.println("The result is : " + result.join());
    }

    /**
     * <p>F<sub>0</sub> = 0
     * <p>F<sub>1</sub> = 1
     * <p>F<sub>n</sub> = F<sub>n-1</sub> + F<sub>n-2</sub>
     */
    static class Fibonacci extends RecursiveTask<Integer> {

        private final int number;

        public Fibonacci(int number) {
            this.number = number;
        }

        @Override
        protected Integer compute() {
            if (number <= 1) {
                return number;
            } else {
                Fibonacci fibonacciMinus1 = new Fibonacci(number - 1);
                Fibonacci fibonacciMinus2 = new Fibonacci(number - 2);
                fibonacciMinus1.fork();
                Integer result = fibonacciMinus2.compute();
                Integer result2 = fibonacciMinus1.join();

                return result + result2;
            }
        }
    }
}