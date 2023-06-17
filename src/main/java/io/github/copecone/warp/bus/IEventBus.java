package io.github.copecone.warp.bus;

public interface IEventBus {
    void register(Object registerClass);
    void unregister(Object registerClass);
    void post(WarpEvent event);
}