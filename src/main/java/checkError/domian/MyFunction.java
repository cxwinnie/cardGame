package checkError.domian;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiandaXu on 2020/6/17.
 */
public class MyFunction {

    private String functionName; //方法名称

    private boolean ifMain = false; //是否为main函数

    private int isrNum = 0; //默认不是中断程序

    private List<GlobalValExp> globalValExpList = new ArrayList();

    private List<AbleISR> ableISRList = new ArrayList<>();

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public boolean isIfMain() {
        return ifMain;
    }

    public void setIfMain(boolean ifMain) {
        this.ifMain = ifMain;
    }

    public int getIsrNum() {
        return isrNum;
    }

    public void setIsrNum(int isrNum) {
        this.isrNum = isrNum;
    }

    public List<GlobalValExp> getGlobalValExpList() {
        return globalValExpList;
    }

    public void setGlobalValExpList(List<GlobalValExp> globalValExpList) {
        this.globalValExpList = globalValExpList;
    }

    public List<AbleISR> getAbleISRList() {
        return ableISRList;
    }

    public void setAbleISRList(List<AbleISR> ableISRList) {
        this.ableISRList = ableISRList;
    }
}