package proxy.dynamicProxy;

/**
 * Created by XiandaXu on 2020/4/20.
 */
public class RealMayun implements Mayun {
    @Override
    public void doSpeech() {
        System.out.println("马云本人来华师大做演讲");
    }
}
