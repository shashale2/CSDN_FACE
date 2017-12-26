package socketServer;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	//服务端的默认端口
	private static int defaultPort = 12345; 
	//用于接受入站的客户端
	private Socket client;
	//用于创建服务并且监听指定端口
	private ServerSocket server;
	public static final String END_FLAG = "over";
	
	public static void main(String[] args) {
		
		System.out.println("yes");
		try {
			EchoServer echoServer = new EchoServer();
			/**
			 * 	ResponseThread用于响应入站的客户端请求的线程
			 * 	一旦有入站请求，将获取其客户端的Socket并为没一个入站的请求启动新的线程单独处理。
			 * */
			ResponseThread responseThread = echoServer.new ResponseThread();
			//启动处理入站请求的线程
			responseThread.start();
			//等待线程
			responseThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public EchoServer() throws IOException {
		//新建服务端的Socket
		server = new ServerSocket(defaultPort);
	}
	
	private class ResponseThread extends Thread {
		
		@Override
		public void run() {
			
			while (true) {
				try {
					//不停的监听入站请求，会阻塞
					client = server.accept();
					//一旦获取到入站的客户端请求则新建HandlerThread线程来单独处理每一个请求
					System.out.println("accpet from client : " + client.getInetAddress() + " , port : " + client.getPort());
					Thread readThread = new HandlerThread(client);
					//启动处理线程
					readThread.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
		
	//用于处理请求的线程
	private class HandlerThread extends Thread {
		//获取客户端的Socket
		private Socket client;
		
		public HandlerThread(Socket client) {
			this.client = client;
		}
		
		@Override
		public void run() {
			BufferedInputStream bis = null;
			BufferedWriter bw = null;
			try {
				//读取和写入流的初始化
				bis = new BufferedInputStream(client.getInputStream());
				bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				String readString = null;
				String writeString = null;
				byte[] buf = new byte[1024];
				String res = new String(buf);
				System.out.println("bugToString0 "+res);

				int len = -1;
				boolean flag= true;
			
				while (true) {
					//从流中获取客户端写入的信息，会阻塞等待
					String res1 = new String(buf);
					System.out.println("bugToString1 "+res1);
					len = bis.read(buf);
					String res2 = new String(buf);
					System.out.println("bugToString2 "+res2);
					readString = new String(buf, 0, len);
					System.out.println("readString " +readString);
					writeString = readString.toUpperCase();
					//判断是否结束当前服务
					if (readString.equalsIgnoreCase(END_FLAG)) {
						break;
					}
					bw.write(writeString);
					bw.flush();
					flag=false;
				}
				flag=false;
				
				//结束Echo信息的发送，将关闭写入流，客户端的读取将会抛出异常结束
				System.out.println("client end client : "  + client.getInetAddress() + " , port : " + client.getPort());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (bw != null) {
					try {
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		
	}
	
}

