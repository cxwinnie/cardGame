package invokeC;

/**
 * Created by XiandaXu on 2020/4/20.
 */
public class HelloWorld {

    public native void sayHello();

    public static void main(String[] args) {
        System.loadLibrary("Win32Project1");
        HelloWorld tNative = new HelloWorld();
        tNative.sayHello();
    }

}
