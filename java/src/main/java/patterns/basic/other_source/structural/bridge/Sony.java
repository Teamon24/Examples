package patterns.basic.other_source.structural.bridge;

import static utils.PrintUtils.println;

public class Sony extends TV {
	int channel = 0;
	public void on() {
		println("Turning on the Sony");
	}
	public void off() {
		println("Turning off the Sony");
	}
	public void tuneChannel(int channel) {
		this.channel = channel;
		println("Set the Sony channel to " + this.channel);
	}
	public int getChannel() {
		return this.channel;
	}
}