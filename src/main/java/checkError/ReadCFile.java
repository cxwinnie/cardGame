package checkError;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by XiandaXu on 2020/6/16.
 */
public class ReadCFile {

    public static StringBuffer readFile(String filePath){
        StringBuffer sb = new StringBuffer();
        try {
            File file = new File(filePath);
            InputStream in = null;
            if (file.isFile() && file.exists()) { //判断文件是否存在
                // 一次读多个字节
                byte[] tempbytes = new byte[1024];
                int byteread = 0;
                in = new FileInputStream(file);
                while ((byteread = in.read(tempbytes)) != -1) {
                    String str = new String(tempbytes, 0, byteread);
                    sb.append(str);
                }
                return sb;
            }
        } catch (Exception e){
            return null;
        }
        return sb;
    }

}
