package patterns.others.structural.bridge;

public class Sony extends TV {
	int channel = 0;
	public void on() {
		System.out.println("Turning on the Sony");
	}
	public void off() {
		System.out.println("Turning off the Sony");
	}
	public void tuneChannel(int channel) {
		this.channel = channel;
		System.out.println("Set the Sony channel to " + this.channel);
	}
	public int getChannel() {
		return channel;
	}
}