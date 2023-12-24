package architecture.patterns.basic.other_source.structural.bridge;

public abstract class TV {
	public abstract void on();
	public abstract void off();
	public abstract void tuneChannel(int channel);
	public abstract int getChannel(); 
}