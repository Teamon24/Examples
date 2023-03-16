package patterns.basic.refactoring.guru.structural.bridge.ex2;

import java.util.function.Consumer;
import java.util.function.Function;

import static utils.ClassUtils.simpleName;
import static java.lang.System.out;

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
        printTest(simpleName(basicRemote));

        for (Consumer<T> command : commands) {
            command.accept(basicRemote);
        }
        device.printStatus();
    }

    private static void printTest(final String name) {
        out.println("------------------------------------");
        out.println("Tests with " + name + " remote.");
        out.println("------------------------------------");
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