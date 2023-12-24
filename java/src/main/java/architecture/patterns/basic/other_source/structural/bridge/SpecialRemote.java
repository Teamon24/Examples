package architecture.patterns.basic.other_source.structural.bridge;

public class SpecialRemote extends RemoteControl {
	public SpecialRemote() {
		super();
	}
	public void up() {
		int channel = this.getChannel();
		this.setChannel(channel+1);
	}
	public void down() {
		int channel = this.getChannel();
		this.setChannel(channel-1);
	}
}