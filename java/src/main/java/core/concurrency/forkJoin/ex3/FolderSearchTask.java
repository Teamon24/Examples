package core.concurrency.forkJoin.ex3;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

class DocumentSearchTask extends RecursiveTask<Long> {
    private final Document document;
    private final String searchedWord;

    DocumentSearchTask(Document document, String searchedWord) {
        super();
        this.document = document;
        this.searchedWord = searchedWord;
    }

    @Override
    protected Long compute() {
        return WordCounter.countWord(document, searchedWord);
    }
}

public class FolderSearchTask extends RecursiveTask<Long> {
    private final Folder folder;
    private final String searchedWord;

    FolderSearchTask(Folder folder, String searchedWord) {
        super();
        this.folder = folder;
        this.searchedWord = searchedWord;
    }

    @Override
    protected Long compute() {
        long count = 0L;
        List<RecursiveTask<Long>> forks = new LinkedList<>();
        for (Folder subFolder : folder.subFolders()) {
            forks.add(forkFolderTask(subFolder));
        }
        for (Document document : folder.documents()) {
            forks.add(forkDocumentTask(document));
        }
        for (RecursiveTask<Long> task : forks) {
            count = count + task.join();
        }
        return count;
    }

    private DocumentSearchTask forkDocumentTask(final Document document) {
        DocumentSearchTask task = new DocumentSearchTask(document, searchedWord);
        task.fork();
        return task;
    }

    private FolderSearchTask forkFolderTask(final Folder subFolder) {
        FolderSearchTask task = new FolderSearchTask(subFolder, searchedWord);
        task.fork();
        return task;
    }
}
