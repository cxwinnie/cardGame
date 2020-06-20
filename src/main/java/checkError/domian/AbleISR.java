package checkError.domian;

/**
 * Created by XiandaXu on 2020/6/19.
 */
public class AbleISR {

    private boolean enable = true; // true:enable   false:disable

    private Integer isrFunction;  //which function

    private Integer lineNum;  // line no

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Integer getIsrFunction() {
        return isrFunction;
    }

    public void setIsrFunction(Integer isrFunction) {
        this.isrFunction = isrFunction;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }
}
