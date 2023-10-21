package Singleton;

public class SingletonHandler {
    private final SingletonClass handlerObject;

    public SingletonHandler() {
        this(SingletonClass.getInstance());
    }

    public SingletonHandler(SingletonClass handler) {
        this.handlerObject = handler;
    }

    public boolean isTestable(){
        return SingletonClass.getInstance().isValid();
    }

    public String getString() {
        return SingletonClass.getInstance().getSomeString();
    }
}
