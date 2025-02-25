package events;

import java.util.function.Consumer;
import java.util.function.Function;

public class Subscriber {
    public final Consumer<Event> consumer;

    public Subscriber(Consumer<Event> consumer) {
        this.consumer = consumer;
    }


}
