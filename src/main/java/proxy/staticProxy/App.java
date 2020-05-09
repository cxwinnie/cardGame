package proxy.staticProxy;

import org.junit.Test;

/**
 * Created by XiandaXu on 2020/4/20.
 */
public class App {

    @Test
    public void test1(){
        Mayun realMayun = new RealMayun(); //马云本人
        Mayun proxyMayun = new ProxyMayun(realMayun); //马云秘书
        proxyMayun.doSpeech();
    }

    @Test
    public void test2(){
        Zhangyong realZhangyong = new RealZhangyong(); //张勇本人
        Zhangyong proxyZhangyong = new ProxyZhangyong(realZhangyong); //马云秘书
        proxyZhangyong.play();
    }

}
