package core.concurrency.thread_pool.forkJoin.ex2;

import java.io.File;
import java.io.IOException;

import static utils.PrintUtils.println;

public class Demo {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File(args[0]));
        println(WordCounter.countWordOnSingleThread(folder, args[1]));
        println(WordCounter.countWordParallel(folder, args[1]));
    }
}
