import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import com.google.gson.*;
import CommonData.GameAndUserData;

public class PlayerIOThread extends Thread
{
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	
	private ArrayList<String> inMessages;
	private ArrayList<String> outMessages;
	
	private Player player;
	private PlayerPool pool;
	
	private boolean finished = false;
	
	private Gson gson;
	
	/**
	 * Handles all input/output for this player
	 * @param socket the socket through which the players connection was accepted.
	 * @param player the player in question
	 * @param pool the main player pool used throughout the server.
	 */
	public PlayerIOThread(Socket socket, Player player, PlayerPool pool)
	{
		super("PlayerIOThread");
		
		this.socket = socket;
		this.player = player;
		this.pool = pool;
		
		inMessages = new ArrayList<String>();
		outMessages = new ArrayList<String>();
		
		gson = new Gson();
	}
	
	@Override
	public void run()
	{
		try
		{
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			handShake();
			addPlayerToPool();
			loop();
		}
		catch(IOException e)
		{
			
		}
		catch(InterruptedException e)
		{
			
		}
	}
	
	/**
	 * Player has exited, end his/her IO thread
	 */
	synchronized public void finishThread()
	{
		finished = true;
	}
	
	synchronized private boolean isFinished()
	{
		return finished;
	}
	
	/**
	 * Continuously checks if client has sent something, or if there is something we should send him.
	 * If there is, sends/receives as appropriate
	 */
	private void loop() throws IOException, InterruptedException
	{
		while(!isFinished()) //Behind a function to ensure thread safety
		{
			if(messagesToSend())
			{
				String msg = getMessageForSending();
				System.out.println("Sending: " + msg);
				out.println(msg);
			}
			
			if(in.ready())
			{
				String msg = in.readLine();
				System.out.println("Receiving: " + msg);
				addMessageForReceiving(msg);
			}
			
			Thread.sleep(100);
		}
		
		cleanUp();
	}
	
	/**
	 * Closes socket and cleans up streams
	 */
	private void cleanUp() throws IOException
	{
		in.close();
		out.close();
		socket.close();
	}
	
	synchronized private void addMessageForReceiving(String message)
	{
		inMessages.add(message);
	}
	
	synchronized private String getMessageForSending()
	{
		return outMessages.remove(0);
	}
	
	synchronized private boolean messagesToSend()
	{
		return outMessages.size() > 0;
	}
	
	synchronized public void sendMessage(String msg)
	{
		outMessages.add(msg);
	}
	
	synchronized public String receiveMessage()
	{
		if(inMessages.size() > 0)
		{
			return inMessages.remove(0);
		}
		
		return null;
	}
	
	/**
	 * Checks if new messages have been received from the server
	 * @return
	 */
	synchronized public boolean messagesReceived()
	{
		return inMessages.size() > 0;
	}
	
	private void addPlayerToPool()
	{
		pool.newPlayer(player);
	}
	
	/**
	 * Works the client through basic handshake.
	 * Makes sure the client is capable of responding to a basic ping thingy
	 * Sets a nickname for the player.
	 * @throws IOException
	 */
	private void handShake() throws IOException
	{
		String input = "";
		int ping = new Random().nextInt();
		
		while((input = in.readLine()) !=  null)
		{
			System.out.println("Receiving: " + input);
			
			if(input.startsWith("HELLO") || input.equals("HELLO"))
			{
				String msg = "PING" + ping;
				System.out.println("Sending: " + msg);
				out.println(msg);
			}
			else if(input.startsWith("PONG"))
			{
				//TODO actually check whether succeeded
				out.println("SUCCESS");
				break;
			}
		}
		
		while((input = in.readLine()) != null)
		{
			System.out.println("Receiving: " + input);
			try
			{
				GameAndUserData data = gson.fromJson(input, GameAndUserData.class);
				System.out.println(data.nickname + " connected. Wishes to play: " + data.gamemode);
				player.setData(data);
				//TODO: Check if nickname is already used
				break;
			}
			catch(Exception e)
			{
				out.println("Invalid JSON: " + e.getMessage());
			}
		}
		
		System.out.println("Handshake'd with " + player.getName());
		
	}
}
