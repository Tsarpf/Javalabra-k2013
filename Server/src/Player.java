import java.net.Socket;
import CommonData.GameAndUserData;

public class Player
{		
	private PlayerIOThread thread;
	
	private GameAndUserData data;
	
	public Player(Socket socket, PlayerPool pool)
	{
		System.out.println("creating new player IO thread");
		thread = new PlayerIOThread(socket, this, pool);
		System.out.println("thread created, starting thread");
		thread.start();
	}
	
	public void setData(GameAndUserData data)
	{
		this.data = data;
	}
	
	
	public String getName()
	{
		return data.nickname;
	}
	
	public void sendMessage(String json)
	{
		thread.sendMessage(json);
	}
	
	public boolean messagesReceived()
	{
		return thread.messagesReceived();
	}
	
	public String receiveMessage()
	{
		return thread.receiveMessage();
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
		
		if(this.getName() == ((Player)other).getName())
		{
			return true;
		}
		
		return false;
	}
	
	@Override
    public int hashCode()
	{
		return getName().hashCode();
	}
}








