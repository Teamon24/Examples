package architecture.patterns.basic.refactoring.guru.structural.bridge.ex2;

import static java.lang.System.out;

public class DeviceUtils {
    public static void message(final String device, boolean isOn, int volume, int channel) {
        out.println("---------- " + device + " STATUS ------------");
        out.println("| I'm " + device + ".");
        out.println("| I'm " + (isOn ? "enabled" : "disabled"));
        out.println("| Current volume is " + volume + "%");
        out.println("| Current channel is " + channel);
        out.println("------------------------------------\n");
    }
}
