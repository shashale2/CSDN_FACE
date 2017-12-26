package socketServer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class MyClient {
	 
public static void main(String[] args) {
MyClient myClient = new MyClient();
myClient.sendFile();
    }
 
public void sendFile() {
        Socket socket = new Socket();
SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 9234);
FileInputStream fileInputStream = null;
OutputStream outputStream = null;
        // 定义缓冲区
byte[] buffer = new byte[1024];
try {
socket.connect(socketAddress);
fileInputStream = new FileInputStream("c:\\xy1.txt");
outputStream = socket.getOutputStream();
int length;
while ((length = fileInputStream.read(buffer, 0, buffer.length)) > 0) {
outputStream.write(buffer, 0, length);
String res1 = new String(buffer);

outputStream.flush();
System.out.println("bugToString1 "+res1);

System.out.println("写入>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.");
            }
        } catch (Exception e) {
e.printStackTrace();
        } finally {
if (fileInputStream != null) {
try {
fileInputStream.close();
                } catch (Exception e) {
e.printStackTrace();
                }
            }
if (outputStream != null) {
try {
outputStream.close();
                } catch (IOException e) {
e.printStackTrace();
                }
            }
if (socket != null &&socket.isConnected()) {
try {
socket.close();
                } catch (IOException e) {
e.printStackTrace();
                }
            }
        }
    }
}