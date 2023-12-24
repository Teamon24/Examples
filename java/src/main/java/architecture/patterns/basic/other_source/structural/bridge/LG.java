package architecture.patterns.basic.other_source.structural.bridge;

import static java.lang.System.out;

public class LG extends TV {
	int channel = 1;
	public void on() {
		out.println("Turning on the LG");
	}
	public void off() {
		out.println("Turning off the LG");
	}
	public void tuneChannel(int channel) {
		this.channel = channel;
		out.println("Set the LG channel to " + this.channel);
	}
	public int getChannel() {
		return channel;
	}
}