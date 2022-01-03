package core.concurrency.forkJoin.ex3;

import java.util.concurrent.ForkJoinPool;

public class WordCounter {

    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public static Long countWord(Document document, String searchedWord) {
        long count = 0;
        for (String line : document.lines()) {
            for (String word : wordsIn(line)) {
                if (searchedWord.equals(word)) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

    static Long countWordParallel(Folder folder, String searchedWord) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
    }

    public static Long countWordOnSingleThread(Folder folder, String searchedWord) {
        long count = 0;
        for (Folder subFolder : folder.subFolders()) {
            count = count + countWordOnSingleThread(subFolder, searchedWord);
        }
        for (Document document : folder.documents()) {
            count = count + countWord(document, searchedWord);
        }
        return count;
    }

}