package patterns.others.structural.bridge;

public class Client {

	private final static String sony = "Sony";
	private final static String lg = "LG";

	public static void main(String[] args) throws Exception {
		final TVFactory tvFactory = new TVFactory();

		final SpecialRemote remoteSony = new SpecialRemote();
		remoteSony.setTV(tvFactory.getTV(sony));
		remoteSony.on();
		remoteSony.up();
		remoteSony.down();
		remoteSony.off();

		final GenericRemote remoteLG = new GenericRemote();
		remoteLG.setTV(tvFactory.getTV(lg));
		remoteLG.on();
		remoteLG.nextChannel();
		remoteLG.prevChannel();
		remoteLG.off();
	}
}