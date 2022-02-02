package patterns.basic.refactoring.guru.structural.bridge.ex2;

import static utils.PrintUtils.println;

public class DeviceUtils {
    public static void message(final String device, boolean isOn, int volume, int channel) {
        println("---------- " + device + " STATUS ------------");
        println("| I'm " + device + ".");
        println("| I'm " + (isOn ? "enabled" : "disabled"));
        println("| Current volume is " + volume + "%");
        println("| Current channel is " + channel);
        println("------------------------------------\n");
    }
}
