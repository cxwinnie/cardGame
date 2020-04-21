package proxy.dynamicProxy;


import java.lang.reflect.Proxy;

/**
 * Created by XiandaXu on 2020/4/20.
 */
public class App {

    public static void main(String[] args) {
        Mayun realMayun = new RealMayun(); //马云本人
        ProxyMayunHandler mayunHandler = new ProxyMayunHandler(realMayun);
        Mayun proxyMayun = (Mayun) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Mayun.class},mayunHandler);
        proxyMayun.doSpeech();
    }

}
