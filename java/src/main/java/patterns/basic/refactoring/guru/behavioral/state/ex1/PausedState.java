package patterns.basic.refactoring.guru.behavioral.state.ex1;

import static patterns.basic.refactoring.guru.behavioral.state.ex1.StateUtils.changedStateMessage;
import static patterns.basic.refactoring.guru.behavioral.state.ex1.StateUtils.stateMessageWithTrack;

/**
 * Они также могут переводить контекст в другие состояния.
 */
public class PausedState extends State {

    public PausedState(Player player) {
        super(player);
    }

    @Override
    public String onLock() {
        StoppedState stoppedState = new StoppedState(player);
        player.changeState(stoppedState);
        changedStateMessage(this, stoppedState);
        return stateMessageWithTrack(this, player.getCurrentTrack());
    }

    @Override
    public String onPlay() {
        PlayingState playingState = new PlayingState(player);
        player.changeState(playingState);
        player.startPlayback();
        changedStateMessage(this, playingState);
        return stateMessageWithTrack(playingState, player.getCurrentTrack());
    }

    @Override
    public String onNext() {
        player.nextTrack();
        return stateMessageWithTrack(this, player.getCurrentTrack());
    }

    @Override
    public String onPrevious() {
        player.previousTrack();
        return stateMessageWithTrack(this, player.getCurrentTrack());
    }
}