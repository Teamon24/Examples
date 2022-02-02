package patterns.basic.refactoring.guru.behavioral.state.ex1;

import static utils.ClassUtils.simpleName;
import static utils.PrintUtils.printfln;

/**
 *
 */
public class StateUtils {
    public static void changedStateMessage(final State prevState, State newState) {
        String prevStateName = simpleName(prevState);
        String newStateName = simpleName(newState);
        printfln("\"%s\" has been changed for \"%s\"", prevStateName, newStateName);
    }

    public static String stateMessageWithTrack(State state, Integer trackNumber) {
        String simpleName = simpleName(state);
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
