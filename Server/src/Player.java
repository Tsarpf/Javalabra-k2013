import java.net.Socket;


public class Player
{	
	String name;
	
	PlayerIOThread thread;
	
	public Player(Socket socket, PlayerPool pool)
	{
		thread = new PlayerIOThread(socket, this, pool);
	}
	
	
	public String getName()
	{
		return name;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other == this)
		{
			return true; 
		}
		
		if(other == null)
		{
			return false;
		}
		
		if (this.getClass() != other.getClass())
		{
			return false;
		}
		
		if(this.name == ((Player)other).getName())
		{
			return true;
		}
		
		return false;
	}
	
	@Override
    public int hashCode()
	{
		return name.hashCode();
	}
}








