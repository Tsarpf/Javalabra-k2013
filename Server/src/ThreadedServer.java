import java.io.IOException;
import java.net.ServerSocket;
import java.net.*;


public class ThreadedServer
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException
	{
		ServerSocket serverSocket = null;
		PlayerPool pool = new PlayerPool();
		
		//TODO: start matchmaking thread
		
		boolean listening = true;
		
		try
		{
			serverSocket = new ServerSocket(5345);
		}
		catch(IOException e)
		{
			System.out.println("Couldn't listen on port 5345");
		}
		
		while(listening)
		{
			System.out.println("Waiting for accept");
			Socket socket = serverSocket.accept();
			
			System.out.println("Accepted, creating new player..");
			//Player player = new Player(socket, pool);
			
			new Player(socket, pool);
		}
	}
}