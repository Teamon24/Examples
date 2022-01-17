package patterns.refactoring.guru.structural.bridge.ex2;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class Client {
    public static void main(String[] args) {
        Tv tv = new Tv();
        tv.enable();
        testRemoteControl(tv);

        Radio radio = new Radio();
        radio.enable();
        testRemoteControl(radio);
    }

    public static void testRemoteControl(Device device) {
        test(device, BasicRemote::new, Client::doCommonCommands);
        test(device, AdvancedRemote::new, Client::doCommonCommands, Client::doAdvancedCommands);
    }

    private static <T extends BasicRemote> void test(Device device,
                                                     Function<Device, T> create,
                                                     Consumer<T> ... commands)
    {

        T basicRemote = create.apply(device);
        printTest(basicRemote.getClass().getSimpleName());

        for (Consumer<T> command : commands) {
            command.accept(basicRemote);
        }
        device.printStatus();
    }

    private static void printTest(final String name) {
        System.out.println("------------------------------------");
        System.out.println("Tests with " + name + " remote.");
        System.out.println("------------------------------------");
    }

    private static void doAdvancedCommands(final AdvancedRemote advancedRemote) {
        advancedRemote.mute();
    }

    private static void doCommonCommands(final BasicRemote basicRemote) {
        basicRemote.channelUp();
        basicRemote.channelUp();
        basicRemote.channelUp();
        basicRemote.volumeDown();
        basicRemote.volumeUp();
    }
}