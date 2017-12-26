package socketServer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	//��ַʹ�õ�ǰ��������
	private static String defaultHost = "localhost";
	//�������˿�Ĭ��ʹ��12345
	private static int defaultPort = 12345; 
	//echo���ԵĽ�����־
	private static final String END_FLAG = "over";
	//�������ӷ������Ŀͻ���Socket
	private Socket connection;
	
	public EchoClient() throws UnknownHostException, IOException {
		//���ӷ���������
		connection = new Socket(defaultHost, defaultPort);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			/**
			 * 	EchoClient�ͻ��˿��������̷ֱ߳���ɲ�ͬ�Ĺ�����
			 * 	��ȡ�߳�ReadThread���ڴӱ�׼���뵱�ж�ȡ�ַ��������Ұ��ַ���д����������
			 * 	д���߳�WriteThread���ڴӷ������˽���Echo�ַ���
			 * */
			EchoClient echoClient = new EchoClient();
			ReadThread readThread = echoClient.new ReadThread();
			WriteThread writThread = echoClient.new WriteThread();
			//�����߳�
			writThread.start();
			readThread.start();
			//�ȴ��߳̽���
			writThread.join();
			readThread.join();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//��ȡ�߳�ReadThread���ڴӱ�׼���뵱�ж�ȡ�ַ��������Ұ��ַ���д����������
	private class ReadThread extends Thread {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			BufferedInputStream bis = null;
			byte[] buf = new byte[1024];
			int readByte = -1;
			String content = null;
			try {
				//ͨ���ͻ���Socket��ȡ�ͷ����ͨ�ŵ�InputStream
				bis = new BufferedInputStream(connection.getInputStream());
				//��ʼѭ������������д����Ҫ�����Echo����
				while (true) {
					/**
					 * �ӷ������Ķ�ȡ���ж�ȡ���ݣ�
					 * ���������û�з���������д�����ݣ���ͻ����޷���ȡ���ݣ���һֱ�����ȴ�
					 */
					readByte = bis.read(buf);
					
					content = new String(buf, 0, readByte);
					System.out.println("<=== Server Echo : " + content);
					/**
					 * �ͻ�������������ص��ַ���Ϊover�ͽ����ͻ���
					 */
					if (content.equalsIgnoreCase(END_FLAG)) {
						System.out.println(">>> Echo End ! <<<");
						break;
					}
				}
			} catch (IOException e) {
				/**
				 * �����������ܵ�������־��ʱ��END_FLAG��ʶ�ͻ�����Ҫ�رշ��񣬷���˵�д��������رգ�
				 * ������whileѭ���пͻ��˵Ķ�ȡ�����ڶ�ȡ����ʱ�ͻ��׳��쳣����ʱ˵���������
				 */
				//System.out.println("Server Write Thread is closed, the client Read Thread closed too");
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//д���߳�WriteThread���ڴӷ������˽���Echo�ַ���
	private class WriteThread extends Thread {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			BufferedReader br = null;
			BufferedOutputStream bos = null;
			String content = null;
			byte[] buf = null;
			try {
				br = new BufferedReader(new InputStreamReader(System.in));
				bos = new BufferedOutputStream(connection.getOutputStream());
				//�ӱ�׼�����л�ȡ�ַ�������д��������������
				while (true) {
					System.out.println("===> Please input :");
					//�ӱ�׼�����ж�ȡ��������
					content = br.readLine();
					buf = content.getBytes();
					//����������ȡ����д������
					bos.write(buf, 0, buf.length);
					bos.flush();
					//�ж��Ƿ�Ϊ������ʶ��������д���߳�
					if (content.equalsIgnoreCase(END_FLAG)) {
						//System.out.println("---> Client echo end!");
						break;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
}

