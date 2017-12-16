package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public final class MyUtil {

    private MyUtil() {
        throw new AssertionError();
    }

    public static void fileCopy(String source, String target) throws IOException {
    	
    	
    		InputStream in = new FileInputStream(source);
    		OutputStream out = new FileOutputStream(target);
    		 byte[] buffer = new byte[4096];
             int bytesToRead;
             while((bytesToRead = in.read(buffer)) != -1) {
                 out.write(buffer, 0, bytesToRead);
             }
          try 
          {
        	  if (in!=null)
        	  {
        		  in.close();
        	  }
        	  if (out!=null)
        	  {
        		  out.close();
        	  }
          }
          finally
          {
        	  System.out.println("关闭成功");
          }
    }

    public void CopyFile2()
    {
    FileReader fr = null;
    FileWriter fw = null;
    try
    {
    fr = new FileReader("c:\\xy1.txt");
    fw = new FileWriter("c:\\xy2.txt");
    char[] charBuffer = new char[1024]; 
    int len = 0;
    while ((len = fr.read(charBuffer)) != -1)
    {
    fw.write(charBuffer, 0, len);
    }
    System.out.println("文件复制成功");
    }
    catch (IOException e)
    {
    throw new RuntimeException("文件拷贝操作失败");
    }
    finally
    {
    try
    {
    if (null != fr)
    {
    fr.close();
    }
    }
    catch (IOException e)
    {
    throw new RuntimeException("关闭失败");
    }

    try
    {
    if (null != fw)
    {
    fw.close();
    }
    }
    catch (IOException e)
    {
    throw new RuntimeException("关闭失败");
    }
    }
    }
    public static void fileCopyNIO(String source, String target) throws IOException {
    	
    }
    
    public static void main(String []  args) throws IOException
    {
    	fileCopy("D:\\tsjhyxx.txt","D:\\tsjhyxx2.txt");
    }
}