package core.concurrency.thread_pool.forkJoin.ex2;

import java.io.File;
import java.io.IOException;

import static java.lang.System.out;

public class Demo {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File(args[0]));
        out.println(WordCounter.countWordOnSingleThread(folder, args[1]));
        out.println(WordCounter.countWordParallel(folder, args[1]));
    }
}
