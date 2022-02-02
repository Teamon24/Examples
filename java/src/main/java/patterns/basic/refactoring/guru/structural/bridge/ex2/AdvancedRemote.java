package patterns.basic.refactoring.guru.structural.bridge.ex2;

import static utils.PrintUtils.println;

public class AdvancedRemote extends BasicRemote {

    private int channelNumber = 0;
    private int prevVolume = 0;

    public AdvancedRemote(Device device) {
        super.device = device;
    }

    public void mute() {
        this.prevVolume = this.device.getVolume();
        println("Remote: mute");
        device.setVolume(0);
    }

    public void unmute() {
        println("Remote: unmute");
        device.setVolume(this.prevVolume);
    }
}