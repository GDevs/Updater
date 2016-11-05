import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import FileMng.DirectoryManager;

public class UpdateClient {
	
	
	public UpdateClient()
	{
		
	}

	public static void main(String args[])
	{
		try {
		DirectoryManager a = new DirectoryManager("UpdateClient");
		Socket s = new Socket("localhost",5555);
		PrintWriter bOut = new PrintWriter(s.getOutputStream());
		System.out.println(s.isBound());
		bOut.println("hi");
		System.out.println("send");
		bOut.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



/*
Socket s = new Socket("localhost",5555);
PrintWriter bOut = new PrintWriter(s.getOutputStream());
System.out.println(s.isBound());
bOut.println("hi");
System.out.println("send");
bOut.close();
s.close();*/