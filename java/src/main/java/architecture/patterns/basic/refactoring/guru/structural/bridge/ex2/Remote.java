package architecture.patterns.basic.refactoring.guru.structural.bridge.ex2;


@Abstraction
public interface Remote {
    void power();

    void volumeDown();

    void volumeUp();

    void channelDown();

    void channelUp();
}