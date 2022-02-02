package patterns.basic.other_source.structural.bridge;

import static utils.PrintUtils.println;

public class LG extends TV {
	int channel = 1;
	public void on() {
		println("Turning on the LG");
	}
	public void off() {
		println("Turning off the LG");
	}
	public void tuneChannel(int channel) {
		this.channel = channel;
		println("Set the LG channel to " + this.channel);
	}
	public int getChannel() {
		return channel;
	}
}