package patterns.basic.other_source.structural.bridge;

import static java.lang.System.out;

public class Sony extends TV {
	int channel = 0;
	public void on() {
		out.println("Turning on the Sony");
	}
	public void off() {
		out.println("Turning off the Sony");
	}
	public void tuneChannel(int channel) {
		this.channel = channel;
		out.println("Set the Sony channel to " + this.channel);
	}
	public int getChannel() {
		return this.channel;
	}
}