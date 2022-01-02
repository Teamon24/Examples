package patterns.others.structural.bridge;

public class GenericRemote extends RemoteControl {
	public GenericRemote() {
		super();
	}
	public void nextChannel() {
		int channel = this.getChannel();
		this.setChannel(channel+1);
	}
	public void prevChannel() {
		int channel = this.getChannel();
		this.setChannel(channel-1);
	}
}