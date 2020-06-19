package checkError.domian;

/**
 * Created by XiandaXu on 2020/6/19.
 */
public class GlobalValExp {

    private String key;

    private String operation;  // R or W

    private String lineNum;  // the line no

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }
}
