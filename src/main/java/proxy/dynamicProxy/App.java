package proxy.dynamicProxy;


import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * Created by XiandaXu on 2020/4/20.
 */
public class App {

    @Test
    public void test1(){
        Mayun realMayun = new RealMayun(); //马云本人
        ProxyHandler mayunHandler = new ProxyHandler(realMayun);
        Mayun proxyMayun = (Mayun) Proxy.newProxyInstance(realMayun.getClass().getClassLoader(),
                new Class[]{Mayun.class},
                mayunHandler);
        proxyMayun.doSpeech();
    }

    @Test
    public void test2(){
        Zhangyong realZhangyong = new RealZhangyong(); //张勇本人
        ProxyHandler zhangyongHandler = new ProxyHandler(realZhangyong);
        Zhangyong proxyZhangyong = (Zhangyong) Proxy.newProxyInstance(realZhangyong.getClass().getClassLoader(),
                new Class[]{Zhangyong.class},
                zhangyongHandler);
        proxyZhangyong.play();
    }

}
