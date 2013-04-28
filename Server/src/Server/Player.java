package Server;
import java.net.Socket;
import CommonData.GameAndUserData;

/**
 * One of these should be instantiated for every client that wishes to play
 * @author Tsarpf
 *
 */
public class Player
{		
	private PlayerIOThread thread;
	
	private GameAndUserData data;
	
	/**
	 * 
	 * @param socket socket using which the player should be communicated with
	 * @param pool main player pool used throughout the server
	 */
	public Player(Socket socket, PlayerPool pool)
	{
		System.out.println("creating new player IO thread");
		thread = new PlayerIOThread(socket, this, pool);
		System.out.println("thread created, starting thread");
		thread.start();
	}
	
	/**
	 * Sets player data acquired during handshake
	 * @param data the data needed for player to be functional in the player pool etc.
	 */
	public void setData(GameAndUserData data)
	{
		this.data = data;
	}
	
	
	public String getName()
	{
		return data.nickname;
	}
	
	/**
	 * Exposes a way to send stuff to the client by using the player's IO thread
	 * @param json JSON string to send. At this point plain text shouldn't be used.
	 */
	public void sendMessage(String json)
	{
		thread.sendMessage(json);
	}
	
	/**
	 * Checks if the client has sent new messages and they are ready for reading.
	 */
	public boolean messagesReceived()
	{
		return thread.messagesReceived();
	}
	
	/**
	 * Gets a message the client has sent to the server
	 */
	public String receiveMessage()
	{
		return thread.receiveMessage();
	}
	
	/**
	 * All players should have an distinct nickname,
	 * so using this players should be distinguishable from eachotehr
	 */
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








