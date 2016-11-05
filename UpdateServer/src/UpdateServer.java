import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import FileMng.DirectoryManager;

/*
 * Funktionen die er haben soll:
 * 
 * 1. Versionsverzeichnis
 * 2. Updatepackete verwalten und überprüfen
 * 3. Verbindungen zu mehreren Clienten verwalten 
 * 
 */
public class UpdateServer implements Runnable {
	
	//STATIC VARIABLES
	static final int CONNECTION_RETRYS = -1;
	static final int WAITINGTIME_MS = 20000;  // 0 = inf  0>
	// IMPORTANT
	Socket clientSocket;
	MessageListener parent;
	PrintWriter serverOut;
	BufferedReader serverIn;
	//LESS IMPORTANT
	private int port;
	
	
	
	
	public UpdateServer(int port)
	{
		this.port = port;
	}
	
	public void run()
	{
		
	}
	
	public static void main(String[] args)
	{
		DirectoryManager dm = new DirectoryManager("ServerUpdater");
		ConnectionManager cm = new ConnectionManager(5555);
		
	}
}
