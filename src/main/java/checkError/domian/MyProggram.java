package checkError.domian;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiandaXu on 2020/6/18.
 */
public class MyProggram {

    private List<DefineVal> defineValList = new ArrayList<>();

    private List<GlobalVal> globalValList = new ArrayList<>();

    private List<MyFunction> myFunctionList = new ArrayList<>();

    public List<GlobalVal> getGlobalValList() {
        return globalValList;
    }

    public void setGlobalValList(List<GlobalVal> globalValList) {
        this.globalValList = globalValList;
    }

    public List<MyFunction> getMyFunctionList() {
        return myFunctionList;
    }

    public void setMyFunctionList(List<MyFunction> myFunctionList) {
        this.myFunctionList = myFunctionList;
    }

    public List<DefineVal> getDefineValList() {
        return defineValList;
    }

    public void setDefineValList(List<DefineVal> defineValList) {
        this.defineValList = defineValList;
    }
}
