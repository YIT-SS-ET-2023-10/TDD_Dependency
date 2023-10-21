package Singleton;

public class SingletonClass {
    private static SingletonClass sInstance;

    private SingletonClass() {
        super();
    }

    public static synchronized SingletonClass getInstance() {
        if (sInstance == null) {
            sInstance = new SingletonClass();
        }
        return sInstance;
    }

    public boolean isValid() {
        System.out.println("Singleton.SingletonClass.isValid() is invoked.");
        return true;
    }

    public String getSomeString() {
        System.out.println("Singleton.SingletonClass.getSomeString() is invoked.");
        return "singleton object";
    }
}
