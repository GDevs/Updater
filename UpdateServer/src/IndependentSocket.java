import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class IndependentSocket extends ServerSocket implements Runnable {
		//STATIC VARIABLES
		static final int CONNECTION_RETRYS = 3;
		static final int WAITINGTIME_MS = 0;  // 0 = inf  0>
		// IMPORTANT
		Socket clientSocket;
		MessageListener parent;
		PrintWriter serverOut;
		BufferedReader serverIn;
		//LESS IMPORTANT
		
		
		/*
		 * Eigenständiger ServerSocket dem ein Port übergeben wird auf 
		 * welchem er nach dem Starten als Thread horcht.
		 */
		public IndependentSocket(int port, MessageListener parent) throws IOException {
			super(port);
			System.out.println(this.getInetAddress());
			this.parent = parent;
			this.setSoTimeout(WAITINGTIME_MS); 
		}

		@Override
		public void run() {
			long lastTime = System.currentTimeMillis();
			
			for(int i = 0; i < CONNECTION_RETRYS || this.isBound(); i++)
			{
				if(clientSocket != null)
				{
					break;
				} 
				else 
				{
					i--;
					try {
						System.out.println("Waiting for connection...");
						clientSocket = accept();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			
			try {
				serverOut = new PrintWriter(clientSocket.getOutputStream());
				serverIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			while(isBound())
			{
				try {
					if(serverIn.ready())
					{
						parent.proccesMessage(serverIn.readLine());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Closing socket");
		}
		
		/*
		 * 
		 */
		synchronized public void receiveFile(String path) throws IOException
		{
			InputStream in = clientSocket.getInputStream();
	        OutputStream out = new FileOutputStream(path);
	        copy(in, out);
	        out.close();
	        in.close();
		}
		
		/*
		 * sends a File;
		 */
		synchronized public void sendFile(File f) throws IOException
		{
			InputStream in = new FileInputStream(f);
			OutputStream out = clientSocket.getOutputStream();
			copy(in, out);
	        out.close();
	        in.close();
			
		}
		
		/*
		 * Takes the input of in bytes and streams it to out
		 */
		static void copy(InputStream in, OutputStream out) throws IOException {
	        byte[] buf = new byte[8192];
	        int len = 0;
	        while ((len = in.read(buf)) != -1) {
	            out.write(buf, 0, len);
	        }
		}
		
		/*
		 * 
		 */
		public void sendMessage(String msg)
		{
			this.serverOut.print(msg);
		}
		
		/*** Getter **/
		
		public boolean isConnected()
		{
			return this.isBound();
		}
		
		public int getPort()
		{
			return this.getLocalPort();
		}
	}
