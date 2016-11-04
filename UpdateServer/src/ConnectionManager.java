
public class ConnectionManager {

	private int maxConnections = 5;
	private IndependentSocket[] connections;
	
	public ConnectionManager()
	{
		
	}
	
	/*
	 * Sets the maximum cound of independent connections. Standard is 5.
	 * 
	 *  If the parameter is <=0 no connections are allowed.
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
	
}
