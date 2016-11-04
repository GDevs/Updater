import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class IndependentSocket extends ServerSocket implements Runnable {
		//STATIC VARIABLES
		static final int CONNECTION_RETRYS = 3;
		static final int WAITINGTIME_MS = 20000;  // 0 = inf  0>
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
			this.parent = parent;
			this.setSoTimeout(WAITINGTIME_MS);
		}

		@Override
		public void run() {
			long lastTime = System.currentTimeMillis();
			
			for(int i = 0; i < CONNECTION_RETRYS || this.isBound(); i++)
			{
				if(this.isBound())
				{
					break;
				} 
				else 
				{
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
			
		}
		
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
