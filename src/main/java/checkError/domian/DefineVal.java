package checkError.domian;

/**
 * Created by XiandaXu on 2020/6/18.
 */
public class DefineVal {

    private String key;  //变量名称

    private Integer value;  //变量值

    private int lineNum;  //变量所在行


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }
}
