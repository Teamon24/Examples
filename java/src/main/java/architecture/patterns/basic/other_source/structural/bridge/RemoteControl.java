package architecture.patterns.basic.other_source.structural.bridge;

public abstract class RemoteControl {
	TV tv;

	public void on() {
		this.tv.on();
	}

	public void off() {
		this.tv.off();
	}

	public void setChannel(int channel) {
		tv.tuneChannel(channel);
	}

	public int getChannel() {
		return tv.getChannel();
	}

	public void setTV(TV tv) {
		this.tv = tv;
	}
}