package patterns.basic.refactoring.guru.structural.proxy.ex1;

import java.util.HashMap;

import static utils.PrintUtils.println;

public class YouTubeDownloader {
    private YoutubeApi api;

    public YouTubeDownloader(YoutubeApi api) {
        this.api = api;
    }

    public void renderVideoPage(String videoId) {
        Video video = api.getVideo(videoId);
        println("\n-------------------------------");
        println("Video page (imagine fancy HTML)");
        println("ID: " + video.id);
        println("Title: " + video.title);
        println("Video: " + video.data);
        println("-------------------------------\n");
    }

    public void renderPopularVideos() {
        HashMap<String, Video> list = api.popularVideos();
        println("\n-------------------------------");
        println("Most popular videos on YouTube (imagine fancy HTML)");
        for (Video video : list.values()) {
            println("ID: " + video.id + " / Title: " + video.title);
        }
        println("-------------------------------\n");
    }
}