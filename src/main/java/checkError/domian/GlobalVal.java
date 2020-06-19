package checkError.domian;

/**
 * Created by XiandaXu on 2020/6/17.
 */

public class GlobalVal {

    private String key;  //变量名称

    private Integer type; //变量类型

    private Object value;  //变量值

    private int lineNum;  //变量所在行

    private boolean isArray = false; //是否是数组

    private Integer arrayLength; //数组长度

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public Integer getArrayLength() {
        return arrayLength;
    }

    public void setArrayLength(Integer arrayLength) {
        this.arrayLength = arrayLength;
    }
}