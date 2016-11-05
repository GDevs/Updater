import java.io.File;
import java.io.IOException;

public class ConnectionManager implements MessageListener {

	public static int port;
	private int maxConnections = 5;
	private IndependentSocket[] connections;
	
	
	/*
	 * needs proper implementation
	 */
	public ConnectionManager(int port)
	{
		this.port = port;
		connections = new IndependentSocket[maxConnections];
		try {
			connections[0] = new IndependentSocket(port,this);
			new Thread(connections[0]).start();
		} catch (IOException e) {
			System.out.println("Failed to create Socket on port: " + port);
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Sets the maximum amount of independent connections. Standard is 5.
	 * 
	 *  If 'max' is <=0 no connections are allowed.
	 */
	public void setMaxConnections(int max)
	{
		this.maxConnections = max;
	}
	
	public void closeConnection()
	{
		
	}
	
	public void closeAllConnection()
	{
		
	}
	
	synchronized public void getConnection()
	{
		
	}

	@Override
	public void proccesMessage(String message) {
		System.out.println(message);
	}
	
	
}
