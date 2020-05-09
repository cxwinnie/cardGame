package proxy.dynamicProxy;

/**
 * Created by XiandaXu on 2020/4/20.
 */
public class RealZhangyong implements Zhangyong {

    @Override
    public void play() {
        System.out.println("张勇来华师大玩");
    }
}
