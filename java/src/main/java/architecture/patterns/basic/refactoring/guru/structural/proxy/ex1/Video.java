package architecture.patterns.basic.refactoring.guru.structural.proxy.ex1;

public class Video {
    public String id;
    public String title;
    public String data;

    Video(String id, String title) {
        this.id = id;
        this.title = title;
        this.data = "Some video.";
    }
}