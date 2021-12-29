package patterns.refactoring.guru.behavioral.state.ex1;

import static patterns.refactoring.guru.behavioral.state.ex1.StateUtils.changedStateMessage;
import static patterns.refactoring.guru.behavioral.state.ex1.StateUtils.stateMessageWithTrack;

public class PlayingState extends State {

    PlayingState(Player player) {
        super(player);
    }

    @Override
    public String onLock() {
        StoppedState stopped = new StoppedState(player);
        player.changeState(stopped);
        changedStateMessage(this, stopped);
        player.setCurrentTrackAfterStop();
        return stateMessageWithTrack(stopped, player.getCurrentTrack());
    }

    @Override
    public String onPlay() {
        PausedState pausedState = new PausedState(player);
        player.changeState(pausedState);
        changedStateMessage(this, pausedState);
        return stateMessageWithTrack(pausedState, player.getCurrentTrack());
    }

    @Override
    public String onNext() {
        player.playNextTrack();
        return stateMessageWithTrack(this, player.getCurrentTrack());
    }

    @Override
    public String onPrevious() {
        player.playPreviousTrack();
        return stateMessageWithTrack(this, player.getCurrentTrack());
    }
}