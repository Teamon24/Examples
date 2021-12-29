package patterns.refactoring.guru.structural.bridge.ex2;

public class DeviceUtils {
    public static void message(final String device, boolean isOn, int volume, int channel) {
        System.out.println("---------- " + device + " STATUS ------------");
        System.out.println("| I'm " + device + ".");
        System.out.println("| I'm " + (isOn ? "enabled" : "disabled"));
        System.out.println("| Current volume is " + volume + "%");
        System.out.println("| Current channel is " + channel);
        System.out.println("------------------------------------\n");
    }
}
