package architecture.patterns.basic.refactoring.guru.structural.proxy.ex1;

import java.util.HashMap;

public interface YoutubeApi {
    HashMap<String, Video> popularVideos();
    Video getVideo(String videoId);
}