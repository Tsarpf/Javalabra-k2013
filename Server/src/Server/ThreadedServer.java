package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.*;

/**
 * Class that contains the main function for the server program.
 * @author Tsarpf
 *
 */
public class ThreadedServer
{
	public static void main(String[] args) throws IOException
	{
		ServerSocket serverSocket = null;
		PlayerPool pool = new PlayerPool();
		
		//TODO: start matchmaking thread
		
		boolean listening = true;
		
		MatchMakingThread mmThread = new MatchMakingThread(pool);
		mmThread.start();
		
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