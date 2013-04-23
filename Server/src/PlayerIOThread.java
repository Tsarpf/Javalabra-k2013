import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;


public class PlayerIOThread extends Thread
{
	Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	private ThreadSafeMessageQueue inQueue;
	private ThreadSafeMessageQueue outQueue;
	
	Player player;
	PlayerPool pool;
	
	public PlayerIOThread(Socket socket, Player player, PlayerPool pool)
	{
		super("ClientHandlerThread");
		
		this.socket = socket;
		this.player = player;
		this.pool = pool;
		
		inQueue = new ThreadSafeMessageQueue();
		outQueue = new ThreadSafeMessageQueue();
	}
	
	public void run()
	{
		try
		{
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			handShake();
			addPlayerToPool();
		}
		catch(IOException e)
		{
			
		}
	}
	
	synchronized public void sendMessage(String msg)
	{
		outQueue.enqueue(msg);
	}
	
	 
	
	private void loop()
	{
		
	}
	
	private void addPlayerToPool()
	{
		pool.newPlayer(player);
	}
	
	private void handShake() throws IOException
	{
		String input = "";
		int ping = new Random().nextInt();
		
		while((input = in.readLine()) !=  null)
		{
			if(input.startsWith("HELLO"))
			{
				out.println("PING" + ping);
			}
			else if(input.startsWith("PONG"))
			{
				//TODO actually check whether succeeded
				out.println("SUCCESS");
				break;
			}
		}
		
	}
	
	public boolean messageReceived() throws IOException
	{
		return in.ready();
	}
	
	public String receiveMessage() throws IOException
	{
		return in.readLine();
	}
	
	public void sendMessage(String message)
	{
		out.println(message);
	}
}
