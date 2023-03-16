package patterns.basic.refactoring.guru.structural.bridge.ex2;

import static java.lang.System.out;

public class AdvancedRemote extends BasicRemote {

    private int channelNumber = 0;
    private int prevVolume = 0;

    public AdvancedRemote(Device device) {
        super.device = device;
    }

    public void mute() {
        this.prevVolume = this.device.getVolume();
        out.println("Remote: mute");
        device.setVolume(0);
    }

    public void unmute() {
        out.println("Remote: unmute");
        device.setVolume(this.prevVolume);
    }
}