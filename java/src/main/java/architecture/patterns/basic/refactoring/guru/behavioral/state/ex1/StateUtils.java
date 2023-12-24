package architecture.patterns.basic.refactoring.guru.behavioral.state.ex1;

import utils.ClassUtils;
import utils.PrintUtils;

/**
 *
 */
public class StateUtils {
    public static void changedStateMessage(final State prevState, State newState) {
        String prevStateName = ClassUtils.simpleName(prevState);
        String newStateName = ClassUtils.simpleName(newState);
        PrintUtils.printfln("\"%s\" has been changed for \"%s\"", prevStateName, newStateName);
    }

    public static String stateMessageWithTrack(State state, Integer trackNumber) {
        String simpleName = ClassUtils.simpleName(state);
        String stateString = null;
        if(PlayingState.class.getSimpleName().equals(simpleName)) {
            stateString = "Playing";
        }
        if(PausedState.class.getSimpleName().equals(simpleName)) {
            stateString = "Paused";
        }

        if(StoppedState.class.getSimpleName().equals(simpleName)) {
            stateString = "Stopped";
        }

        if (stateString == null) throw new RuntimeException("Unexpected class: " + simpleName);
        return String.format("%s: current track %s", stateString, trackNumber);
    }
}
