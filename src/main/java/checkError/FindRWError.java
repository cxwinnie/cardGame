package checkError;

import checkError.domian.GlobalVal;
import checkError.domian.GlobalValExp;
import checkError.domian.MyFunction;
import checkError.domian.MyProggram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiandaXu on 2020/6/20
 * find the read and write error
 */
public class FindRWError {

    public static List errorList = new ArrayList();

    public static void findRWError(MyProggram myProggram) {
        detailOperation(myProggram, "R", "W", "R");
        detailOperation(myProggram, "W", "W", "R");
        detailOperation(myProggram, "R", "W", "W");
        detailOperation(myProggram, "W", "R", "W");
    }

    public static List detailOperation(MyProggram myProggram,
                                       String operation1, String operation2, String operation3) {
        List<MyFunction> myFunctionList = myProggram.getMyFunctionList();
        if (myFunctionList.size() <= 1) {
            return null;
        } else {
            List<GlobalVal> globalValList = myProggram.getGlobalValList();
            for (GlobalVal globalVal : globalValList) {
                String globalValKey = globalVal.getKey();
                for (int i = 0; i < myFunctionList.size(); i++) {
                    boolean op1Flag = false;
                    boolean op2Flag = false;
                    boolean op3Flag = false;
                    GlobalValExp g1 = new GlobalValExp();
                    GlobalValExp g2 = new GlobalValExp();
                    GlobalValExp g3 = new GlobalValExp();
                    MyFunction myFunction = myFunctionList.get(i);
                    for (GlobalValExp globalValExp : myFunction.getGlobalValExpList()) {
                        if (globalValExp.getKey().equals(globalValKey)) {
                            if (!op1Flag && globalValExp.getOperation().equals(operation1)) {
                                op1Flag = true;
                                g1.setLineNum(globalValExp.getLineNum());
                                g1.setOperation(operation1);
                            } else if (op1Flag && !op3Flag && globalValExp.getOperation().equals(operation3)) {
                                g3.setLineNum(globalValExp.getLineNum());
                                g3.setOperation(operation3);
                                op3Flag = true;
                            }
                            if (op1Flag && op3Flag) {
                                break;
                            }
                        }
                    }
                    if (op1Flag && op3Flag) {
                        for (int j = 0; j < myFunctionList.size(); j++) {
                            if (j != i) {
                                myFunction = myFunctionList.get(j);
                                for (GlobalValExp globalValExp : myFunction.getGlobalValExpList()) {
                                    if (globalValExp.getKey().equals(globalValKey)) {
                                        if (!op2Flag && globalValExp.getOperation().equals(operation2)) {
                                            g2.setLineNum(globalValExp.getLineNum());
                                            g2.setOperation(operation2);
                                            op2Flag = true;
                                            break;
                                        }
                                    }
                                }
                                if (op2Flag) {
                                    break;
                                }
                            }
                        }
                    }
                    if (op1Flag && op2Flag && op3Flag) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(g1.getOperation()).append(":").append(g1.getLineNum()).append("  ")
                                .append(g2.getOperation()).append(":").append(g2.getLineNum()).append("  ")
                                .append(g3.getOperation()).append(":").append(g3.getLineNum());
                        errorList.add(sb.toString());
                    }
                }

            }

            return errorList;
        }
    }


}
