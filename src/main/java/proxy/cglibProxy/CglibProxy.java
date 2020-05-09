package proxy.cglibProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by XiandaXu on 2020/4/21.
 */
public class CglibProxy implements MethodInterceptor {


    public Object getInstance(final Object target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());  //设置父类
        enhancer.setCallback(this);  //设置回调
        return enhancer.create();  //根据target对象创建该对象的代理对象
    }

    /* 接受邀请 */
    public void acceptInvition(){
        System.out.println("马云的秘书接受邀请");
    }

    /* 安排时间 */
    public void arrangeTime(){
        System.out.println("马云的秘书安排出行方式和时间");
    }

    /* 安排吃饭 */
    public void doLunch(){
        System.out.println("马云的秘书安排午饭");
    }

    /* 安排回城 */
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
