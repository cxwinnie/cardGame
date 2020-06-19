package checkError.domian;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiandaXu on 2020/6/17.
 */
public class MyFunction {

    private String functionName; //方法名称

    private List localValArrayList = new ArrayList<LocalVal>();

    private int start = 0;  //方法的起始行

    private int end = 0;   //方法的结束行

    private boolean ifMain = false; //是否为main函数

    private int isrNum = 0; //默认不是中断程序

    private boolean executeNow = false; //现在是否能执行

    private List<GlobalValExp> globalValExpList = new ArrayList();

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List getLocalValArrayList() {
        return localValArrayList;
    }

    public void setLocalValArrayList(List localValArrayList) {
        this.localValArrayList = localValArrayList;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
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

    public boolean isExecuteNow() {
        return executeNow;
    }

    public void setExecuteNow(boolean executeNow) {
        this.executeNow = executeNow;
    }
}
class LocalVal{
    private String key;  //变量名称
    private Object value;  //变量值
    private int lineNum;  //变量所在行

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}