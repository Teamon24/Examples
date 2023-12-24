package architecture.patterns.basic.refactoring.guru.structural.proxy.ex1;

import java.util.HashMap;

import static java.lang.System.out;

public class YouTubeDownloader {
    private YoutubeApi api;

    public YouTubeDownloader(YoutubeApi api) {
        this.api = api;
    }

    public void renderVideoPage(String videoId) {
        Video video = api.getVideo(videoId);
        out.println("\n-------------------------------");
        out.println("Video page (imagine fancy HTML)");
        out.println("ID: " + video.id);
        out.println("Title: " + video.title);
        out.println("Video: " + video.data);
        out.println("-------------------------------\n");
    }

    public void renderPopularVideos() {
        HashMap<String, Video> list = api.popularVideos();
        out.println("\n-------------------------------");
        out.println("Most popular videos on YouTube (imagine fancy HTML)");
        for (Video video : list.values()) {
            out.println("ID: " + video.id + " / Title: " + video.title);
        }
        out.println("-------------------------------\n");
    }
}