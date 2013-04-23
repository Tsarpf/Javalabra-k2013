import java.net.Socket;
import CommonData.GameAndUserData;

public class Player
{		
	PlayerIOThread thread;
	
	GameAndUserData data;
	
	public Player(Socket socket, PlayerPool pool)
	{
		thread = new PlayerIOThread(socket, this, pool);
	}
	
	public void setData(GameAndUserData data)
	{
		this.data = data;
	}
	
	
	public String getName()
	{
		return data.nickname;
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








