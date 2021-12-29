package patterns.others.structural.wrapper.bridge.ex1;

public class LG extends TV {
	int channel = 1;
	public void on() {
		System.out.println("Turning on the LG");
	}
	public void off() {
		System.out.println("Turning off the LG");
	}
	public void tuneChannel(int channel) {
		this.channel = channel;
		System.out.println("Set the LG channel to " + this.channel);
	}
	public int getChannel() {
		return channel;
	}
}