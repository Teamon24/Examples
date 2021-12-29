package patterns.others.structural.wrapper.bridge.ex1;

public class Client {

	private final static String sony = "Sony";
	private final static String lg = "LG";

	public static void main(String[] args) {
		final TVFactory tvFactory = new TVFactory();

		final SpecialRemote remoteSony = new SpecialRemote(tvFactory);
		remoteSony.setTV(sony);
		remoteSony.on();
		remoteSony.up();
		remoteSony.down();
		remoteSony.off();

		final GenericRemote remoteLG = new GenericRemote(tvFactory);
		remoteLG.setTV(lg);
		remoteLG.on();
		remoteLG.nextChannel();
		remoteLG.prevChannel();
		remoteLG.off();
	}
}