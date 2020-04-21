package proxy.staticProxy;

/**
 * Created by XiandaXu on 2020/4/20.
 */
public class App {

    public static void main(String[] args) {
        Mayun realMayun = new RealMayun(); //马云本人
        Mayun proxyMayun = new ProxyMayun(realMayun); //马云秘书
        proxyMayun.doSpeech();
    }

}
