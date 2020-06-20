package checkError;

import checkError.domian.*;

import java.util.List;
import java.util.Stack;

/**
 * Created by XiandaXu on 2020/6/18.
 * find the global value、function and local value
 */
public class ProcessProgKeyWord {

    private static int functionStartLine = 0;
    private static int functionEndLine = 0;

    public static void dealWith(MyProggram myProggram) {
        List<ProgKeyWord> listProgKeyWord = ProgProcess.listProgKeyWord;
        findDefineValue(myProggram, listProgKeyWord);
        findGlobalValue(myProggram, listProgKeyWord);
        findFuntion(myProggram, listProgKeyWord);
    }

    /**
     * find the define value
     *
     * @param myProggram
     * @param listProgKeyWord
     */
    public static void findDefineValue(MyProggram myProggram, List<ProgKeyWord> listProgKeyWord) {
        for (int i = 0; i < listProgKeyWord.size(); i++) {
            ProgKeyWord progKeyWord = listProgKeyWord.get(i);
            if (progKeyWord.getIndex() == 84) {  // #define
                ProgKeyWord next1progKeyWord = listProgKeyWord.get(i + 1);
                ProgKeyWord next2progKeyWord = listProgKeyWord.get(i + 2);
                DefineVal defineVal = new DefineVal();
                defineVal.setKey(next1progKeyWord.getKey());
                defineVal.setValue(new Integer(next2progKeyWord.getKey()));
                myProggram.getDefineValList().add(defineVal);
            }
        }
    }

    /**
     * find the global value
     *
     * @param myProggram
     * @param listProgKeyWord
     */
    public static void findGlobalValue(MyProggram myProggram, List<ProgKeyWord> listProgKeyWord) {

        for (int i = 0; i < listProgKeyWord.size(); i++) {
            ProgKeyWord progKeyWord = listProgKeyWord.get(i);
            ProgKeyWord nextProgKeyWord = null;
            if (i != listProgKeyWord.size() - 1) {
                nextProgKeyWord = listProgKeyWord.get(i + 1);
            }
            if (progKeyWord.getKey().contains("main") && "(".equals(nextProgKeyWord.getKey())) {
                functionStartLine = progKeyWord.getLineNum(); // access the lineNum of the main function
                break; //find the main function and exit
            }

            if (progKeyWord.getIndex().intValue() == 81) {
                ProgKeyWord preProgKeyWord = listProgKeyWord.get(i - 1);
                ProgKeyWord post1ProgKeyWord = listProgKeyWord.get(i + 1);
                int index = preProgKeyWord.getIndex().intValue();
                if (index == 3 || index == 9 || index == 13
                        || index == 17 || index == 18 || index == 21) {  //int char float double short long
                    GlobalVal globalVal = new GlobalVal();
                    globalVal.setKey(progKeyWord.getKey());
                    globalVal.setType(index);
                    globalVal.setLineNum(progKeyWord.getLineNum());
                    ProgKeyWord post2ProgKeyWord = listProgKeyWord.get(i + 2);
                    if (post1ProgKeyWord.getKey().equals("=")) {
                        globalVal.setValue(post2ProgKeyWord.getKey());
                    } else if (post1ProgKeyWord.getKey().equals(";")) {

                    } else {  // if post1ProgKeyWord.getKey().equals("[")
                        globalVal.setArray(true);
                        String post2 = post2ProgKeyWord.getKey();
                        if (!containAlpha(post2)) {
                            globalVal.setArrayLength(new Integer(post2));
                        } else { //if contains the defineval
                            List<DefineVal> defineValList = myProggram.getDefineValList();
                            for (DefineVal defineVal : defineValList) {  // traverse the defineValList
                                if (defineVal.getKey().equals(post2)) {
                                    globalVal.setArrayLength(defineVal.getValue());
                                    break;
                                }
                            }
                        }
                    }
                    myProggram.getGlobalValList().add(globalVal);
                }
            }
        }
    }

    public static void findFuntion(MyProggram myProggram, List<ProgKeyWord> listProgKeyWord) {
        Stack stack = new Stack();
        Stack parenthesesStack = new Stack();  // ()stack
        int i = 0;
        while (listProgKeyWord.get(i).getLineNum() != functionStartLine) {
            i++; // find the first line of the function
        }
        boolean funcFlag = false; // mark if in the function
        boolean stackFlag = false;
        boolean equalFlag = false; // mark the =
        MyFunction myFunction = new MyFunction();
        for (; i < listProgKeyWord.size(); i++) {
            ProgKeyWord progKeyWord = listProgKeyWord.get(i);
            if (!funcFlag) {
                if (progKeyWord.getIndex() == 81) {
                    if (progKeyWord.getKey().contains("main")) {
                        myFunction.setIfMain(true);
                    }
                    funcFlag = true; // find the custom function name
                    myFunction.setFunctionName(progKeyWord.getKey());
                }
            } else {
                if (progKeyWord.getKey().equals("{")) {
                    stackFlag = true;
                    stack.push(1);
                } else if (progKeyWord.getKey().equals("}")) {
                    stack.pop();
                } else if (progKeyWord.getKey().equals("(")) {
                    parenthesesStack.push(1);
                } else if (progKeyWord.getKey().equals(")")) {
                    parenthesesStack.pop();
                } else if (progKeyWord.getKey().equals("=") && parenthesesStack.isEmpty()) { //prevent if( int i= 1)
                    equalFlag = true;
                } else if (progKeyWord.getKey().equals(";") && parenthesesStack.isEmpty()) {
                    equalFlag = false;
                } else if (progKeyWord.getIndex() == 81) {
                    if (progKeyWord.getKey().equals("enable_isr")
                            || progKeyWord.getKey().equals("disable_isr")) {
                        ProgKeyWord post2ProgKeyWord = listProgKeyWord.get(i + 2);
                        AbleISR ableISR = new AbleISR();
                        ableISR.setIsrFunction(new Integer(post2ProgKeyWord.getKey()));
                        ableISR.setLineNum(progKeyWord.getLineNum());
                        if (progKeyWord.getKey().equals("disable_isr")) {
                            ableISR.setEnable(false);
                        }
                        myFunction.getAbleISRList().add(ableISR);
                    } else {
                        GlobalVal findGlobal = findGlobalVal(myProggram.getGlobalValList(), progKeyWord.getKey());
                        if (findGlobal != null && parenthesesStack.isEmpty()) {
                            GlobalValExp globalValExp = new GlobalValExp();
                            globalValExp.setKey(progKeyWord.getKey());
                            globalValExp.setLineNum(progKeyWord.getLineNum());
                            if (!equalFlag) {
                                globalValExp.setOperation("W");
                            } else {
                                globalValExp.setOperation("R");
                            }
                            ProgKeyWord post2ProgKeyWord = listProgKeyWord.get(i + 2);
                            if (findGlobal.isArray()) {
                                if (post2ProgKeyWord.getKey().equals("TRIGGER") || post2ProgKeyWord.getKey().equals("i")) {
                                    globalValExp.setKey(progKeyWord.getKey() + "[TRIGGER]");
                                    GlobalVal globalVal = new GlobalVal();
                                    addGlobalVarrayValue(myProggram, progKeyWord.getKey() + "[TRIGGER]");
                                } else {
                                    globalValExp.setKey(progKeyWord.getKey() + "[" + post2ProgKeyWord.getKey() + "]");
                                    addGlobalVarrayValue(myProggram, progKeyWord.getKey() + "[" + post2ProgKeyWord.getKey() + "]");
                                }
                            }
                            myFunction.getGlobalValExpList().add(globalValExp);
                        }
                    }
                }

                if (stackFlag && stack.isEmpty()) {  //find a whole function and search the next function
                    myProggram.getMyFunctionList().add(myFunction);
                    funcFlag = false;
                    stackFlag = false;
                    myFunction = new MyFunction();
                }
            }
        }

    }

    public static boolean containAlpha(String string) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (!(c >= '0' && c <= '9')) {
                return true;
            }
        }
        return false;
    }

    public static GlobalVal findGlobalVal(List<GlobalVal> globalValList, String key) {
        for (GlobalVal globalVal : globalValList) {
            if (globalVal.getKey().equals(key)) {
                return globalVal;
            }
        }
        return null;
    }

    public static void addGlobalVarrayValue(MyProggram myProggram, String key) {

        for (GlobalVal globalVal : myProggram.getGlobalValList()){
            if(globalVal.getKey().equals(key)){
                return ;
            }
        }
        GlobalVal globalVal = new GlobalVal();
        globalVal.setKey(key);
        myProggram.getGlobalValList().add(globalVal);
    }

}
