package architecture.patterns.basic.refactoring.guru.behavioral.state.ex1;

import static architecture.patterns.basic.refactoring.guru.behavioral.state.ex1.StateUtils.changedStateMessage;
import static architecture.patterns.basic.refactoring.guru.behavioral.state.ex1.StateUtils.stateMessageWithTrack;

/**
 * Конкретные состояния реализуют методы абстрактного состояния по-своему.
 */
public class StoppedState extends State {

    StoppedState(Player player) {
        super(player);
        player.setPlaying(false);
    }

    @Override
    public String onLock() {
        if (player.isPlaying()) {
            PausedState pausedState = new PausedState(player);
            player.changeState(pausedState);
            changedStateMessage(this, pausedState);
            return stateMessageWithTrack(pausedState, player.getCurrentTrack());
        } else {
            return stateMessageWithTrack(this, player.getCurrentTrack());
        }
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
        return stateMessageWithTrack(this, player.getCurrentTrack());
    }

    @Override
    public String onPrevious() {
        return stateMessageWithTrack(this, player.getCurrentTrack());
    }
}