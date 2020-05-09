package proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by XiandaXu on 2020/4/20.
 */
public class ProxyHandler implements InvocationHandler {

    private Object object;

    public ProxyHandler(Object object) {
        this.object = object;
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

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        acceptInvition();
        arrangeTime();
        method.invoke(object, args);
        doLunch();
        returnHome();
        return null;
    }

}
