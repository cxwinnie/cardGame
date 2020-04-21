package proxy.staticProxy;

/**
 * Created by XiandaXu on 2020/4/20.
 */
public class ProxyMayun implements Mayun {

    private Mayun realMayun;

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


    public ProxyMayun(Mayun mayun){
        this.realMayun = mayun;
    }

    /**
     * 做演讲
     */
    @Override
    public void doSpeech() {
        acceptInvition();
        arrangeTime();
        realMayun.doSpeech();
        doLunch();
        returnHome();
    }

}
