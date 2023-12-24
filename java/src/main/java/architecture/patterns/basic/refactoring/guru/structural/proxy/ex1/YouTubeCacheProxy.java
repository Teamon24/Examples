package architecture.patterns.basic.refactoring.guru.structural.proxy.ex1;

import java.util.HashMap;

import static java.lang.System.out;

public class YouTubeCacheProxy implements YoutubeApi {

    private YoutubeApi youtubeService;
    private HashMap<String, Video> cachePopular = new HashMap<String, Video>();
    private HashMap<String, Video> cacheAll = new HashMap<String, Video>();

    public YouTubeCacheProxy() {
        this.youtubeService = new YoutubeApiImpl();
    }

    @Override
    public HashMap<String, Video> popularVideos() {
        if (this.cachePopular.isEmpty()) {
            this.cachePopular = this.youtubeService.popularVideos();
        } else {
            out.println("Retrieved list from cache.");
        }
        return this.cachePopular;
    }

    @Override
    public Video getVideo(String videoId) {
        Video video = this.cacheAll.get(videoId);
        if (video == null) {
            video = this.youtubeService.getVideo(videoId);
            this.cacheAll.put(videoId, video);
        } else {
            out.println("Retrieved video '" + videoId + "' from cache.");
        }
        return video;
    }

    public void reset() {
        this.cachePopular.clear();
        this.cacheAll.clear();
    }
}