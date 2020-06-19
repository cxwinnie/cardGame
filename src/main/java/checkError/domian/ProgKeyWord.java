package checkError.domian;

/**
 * Created by XiandaXu on 2020/6/18.
 */
public class ProgKeyWord {

    private String key;

    private Integer index;

    private Integer lineNum;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getLineNum() {
        return lineNum;
    }

    public void setLineNum(Integer lineNum) {
        this.lineNum = lineNum;
    }

    public ProgKeyWord(String key, Integer index, Integer lineNum) {
        this.key = key;
        this.index = index;
        this.lineNum = lineNum;
    }
}
