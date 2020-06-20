package checkError;

import checkError.domian.AbleISR;
import checkError.domian.MyFunction;
import checkError.domian.MyProggram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiandaXu on 2020/6/20.
 */
public class FindLockError {

    public static List errorList = new ArrayList();

    public static void findLockError(MyProggram myProggram) {
        // find the disable_isr,disable_isr,enable_isr
        List<MyFunction> myFunctionList = myProggram.getMyFunctionList();
        if (myFunctionList.size() <= 1) {
            return;
        }
        for (int i = 0; i < myFunctionList.size(); i++) {
            MyFunction myFunction = myFunctionList.get(i);
            List<AbleISR> ableISRList = myFunction.getAbleISRList();
            for (AbleISR ableISR : ableISRList) {
                Integer isrFunction = ableISR.getIsrFunction();
                boolean enable = ableISR.isEnable();
                if (!enable) {
                    for (int j = 0; j < myFunctionList.size(); j++) {
                        if (j != i) {
                            List<AbleISR> tempISRList = myFunctionList.get(j).getAbleISRList();
                            for (int k = 0; k < tempISRList.size() - 1; k++) {
                                if (tempISRList.get(k).getIsrFunction().equals(isrFunction)
                                        && tempISRList.get(k).isEnable() == false
                                        && tempISRList.get(k+1).getIsrFunction().equals(isrFunction)
                                        && tempISRList.get(k+1).isEnable() == true) {
                                    StringBuffer sb = new StringBuffer();
                                    sb.append(ableISR.getLineNum()).append(",")
                                    .append(tempISRList.get(k).getLineNum()).append(",")
                                    .append(tempISRList.get(k+1).getLineNum());
                                    errorList.add(sb.toString());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
