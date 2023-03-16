package core.concurrency.package_review.thread_factory;

import org.apache.commons.lang3.StringUtils;
import utils.ConcurrencyUtils;

import java.util.concurrent.ThreadFactory;

public class CustomThreadFactory implements ThreadFactory {
    private int threadId;
    private String name;

    public CustomThreadFactory(String name) {
        this.threadId = 1;
        this.name = name;
    }

    public CustomThreadFactory() {
        this.threadId = 1;
        this.name = "";
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable, name());
        String message = String.format("thread \"%s\" was created", thread.getName());
        ConcurrencyUtils.threadPrintln(message);
        this.threadId++;
        return thread;
    }

    private String name() {
        if (StringUtils.isEmpty(this.name)) {
            return "#" + this.threadId;
        } else {
            return this.name + ":#" + this.threadId;
        }
    }
}