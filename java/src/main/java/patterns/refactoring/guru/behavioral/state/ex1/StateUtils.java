package patterns.refactoring.guru.behavioral.state.ex1;

/**
 *
 */
public class StateUtils {
    public static void changedStateMessage(final State prevState, State newState) {
        String prevStateName = prevState.getClass().getSimpleName();
        String newStateName = newState.getClass().getSimpleName();
        System.out.printf("\"%s\" has been changed for \"%s\"\n", prevStateName, newStateName);
    }

    public static String stateMessageWithTrack(State state, Integer trackNumber) {
        String simpleName = state.getClass().getSimpleName();
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
