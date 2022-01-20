package patterns.basic.refactoring.guru.behavioral.state.ex1;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private State state;
    private boolean playing = false;
    private List<String> playlist = new ArrayList<>();
    private int currentTrack = 0;

    public Player() {
        this.state = new PausedState(this);
        setPlaying(true);
        for (int i = 1; i <= 12; i++) {
            playlist.add("Track " + i);
        }
    }

    public int getCurrentTrack() {
        return currentTrack + 1;
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isPlaying() {
        return playing;
    }

    public String startPlayback() {
        return StateUtils.stateMessageWithTrack(this.getState(), this.getCurrentTrack());
    }

    public String nextTrack() {
        return nextTrackLogic();
    }

    public String playNextTrack() {
        return "Playing " + nextTrackLogic();
    }

    public String previousTrack() {
        return prevTrackLogic();
    }

    public String playPreviousTrack() {
        return "Playing " + prevTrackLogic();
    }

    private String nextTrackLogic() {
        currentTrack++;
        if (currentTrack > playlist.size() - 1) {
            currentTrack = 0;
        }
        String track = playlist.get(currentTrack);
        return track;
    }

    private String prevTrackLogic() {
        currentTrack--;
        if (currentTrack < 0) {
            currentTrack = playlist.size() - 1;
        }
        String track = playlist.get(currentTrack);
        return track;
    }

    public void setCurrentTrackAfterStop() {
        this.currentTrack = 0;
    }
}