package proxy.cglibProxy;

/**
 * Created by XiandaXu on 2020/4/21.
 */
public class App {

    public static void main(String[] args) {
        RealMayun realMayun = new RealMayun();
        CglibProxy cglibProxy = new CglibProxy();
        RealMayun mayunCglibProxy = (RealMayun)cglibProxy.getInstance(realMayun);
        mayunCglibProxy.doSpeech();
    }

}
