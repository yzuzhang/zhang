package com.feicent.zhang.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.feicent.zhang.util.CloseUtil;
import com.feicent.zhang.util.date.DateUtils;

/**
 * 将内容追加到文件尾部
 * @author yzuzhang
 */
public class AppendToFile {
	
    public static void appendMethodA(String fileName, String content) {
    	RandomAccessFile randomFile = null;
    	try {
            // 打开一个随机访问文件流，按读写方式
            randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            //randomFile.writeChars(content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	CloseUtil.close(randomFile);
        }
    }

   
    public static void appendMethodB(String fileName, String content) {
    	FileWriter writer = null;
    	try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            writer.write(content);
            
            //工具类的使用
            //FileUtils.writeStringToFile(new File(fileName), content, true);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	CloseUtil.close(writer);
        }
    }
    
    public static void byteArrayOutputStream() throws Exception
    {
    	String str = "hello admin";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(str.getBytes());
        
        int length = str.length();
        byte [] b = new byte[length];
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());//ByteArrayInputStream： 专门用来从内存中读取数据的流
        in.read(b);
        System.out.println(Arrays.toString(b));
    }
    
    public static void lineNumberReader(String filePath) throws IOException {
        LineNumberReader lnr = new LineNumberReader(new FileReader(filePath));
        //public void setLineNumber(int lineNumber)设置当前行号。 如果不设置该值 行号默认从0开始
        lnr.setLineNumber(10);
        String str=null;
        while((str=lnr.readLine())!=null){
            //public int getLineNumber()获得当前行号。 
            System.out.println(lnr.getLineNumber()+":"+str);
        }
        lnr.close();
    }
    
    public static void main(String[] args) throws Exception {
        String fileName = "D:/temp/newTemp.txt";
        String content = "new append!"+ DateUtils.getNow() +"\r\n";
        
        //按方法A追加文件
        AppendToFile.appendMethodA(fileName, content);
        AppendToFile.appendMethodA(fileName, "appendMethodA end.\r\n");
        //显示文件内容
        ReadFromFile.readFileByLines(fileName);
        
        //按方法B追加文件
        AppendToFile.appendMethodB(fileName, content);
        AppendToFile.appendMethodB(fileName, "appendMethodB end. \r\n");
        //显示文件内容
        ReadFromFile.readFileByLines(fileName);
        
        byteArrayOutputStream();
        
        lineNumberReader(fileName);
    }
    
}
