import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class UpdateClient {
	
	
	public UpdateClient()
	{
		
	}

	public static void main(String args[])
	{
		try {
			Socket s = new Socket("localhost",5555);
			PrintWriter bOut = new PrintWriter(s.getOutputStream());
			System.out.println(s.isBound());
			bOut.println("hi");
			System.out.println("send");
			bOut.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
