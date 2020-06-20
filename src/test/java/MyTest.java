import checkError.FindLockError;
import checkError.ProcessProgKeyWord;
import checkError.ProgProcess;
import checkError.FindRWError;
import checkError.domian.MyProggram;
import org.junit.Test;

/**
 * Created by XiandaXu on 2020/6/18.
 */
public class MyTest {

    @Test
    public void test1(){
        ProgProcess.process("X:\\ecnu\\软件分析与验证工具\\期末project\\基准测试集\\svp_simple_005\\svp_simple_005_001.c");
        MyProggram myProggram = new MyProggram();
        ProcessProgKeyWord.dealWith(myProggram);
        FindRWError.findRWError(myProggram);
        System.out.println(FindRWError.errorList);
        FindLockError.findLockError(myProggram);
        System.out.println(FindLockError.errorList);
    }

}
