package proxy.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by XiandaXu on 2020/4/21.
 */
public class CglibProxy implements MethodInterceptor {

    private Object target;

    public Object getInstance(final Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    /**
     * 接受邀请
     */
    public void acceptInvition(){
        System.out.println("马云的秘书接受邀请");
    }

    /**
     * 安排时间
     */
    public void arrangeTime(){
        System.out.println("马云的秘书安排出行方式和时间");
    }

    /**
     * 安排吃饭
     */
    public void doLunch(){
        System.out.println("马云的秘书安排午饭");
    }

    /**
     * 安排回城
     */
    public void returnHome(){
        System.out.println("马云的秘书安排回杭州");
    }

    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        acceptInvition();
        arrangeTime();
        methodProxy.invokeSuper(object, args);
        doLunch();
        returnHome();
        return null;
    }

}
