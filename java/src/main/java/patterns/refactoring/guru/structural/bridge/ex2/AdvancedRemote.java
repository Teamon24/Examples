package patterns.refactoring.guru.structural.bridge.ex2;

public class AdvancedRemote extends BasicRemote {

    private int channelNumber = 0;
    private int prevVolume = 0;

    public AdvancedRemote(Device device) {
        super.device = device;
    }

    public void mute() {
        this.prevVolume = this.device.getVolume();
        System.out.println("Remote: mute");
        device.setVolume(0);
    }

    public void unmute() {
        System.out.println("Remote: unmute");
        device.setVolume(this.prevVolume);
    }
}