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
	Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	
	private ArrayList<String> inMessages;
	private ArrayList<String> outMessages;
	
	Player player;
	PlayerPool pool;
	
	boolean finished = false;
	
	Gson gson;
	
	public PlayerIOThread(Socket socket, Player player, PlayerPool pool)
	{
		super("ClientHandlerThread");
		
		this.socket = socket;
		this.player = player;
		this.pool = pool;
		
		inMessages = new ArrayList<String>();
		outMessages = new ArrayList<String>();
		
		gson = new Gson();
	}
	
	public void run()
	{
		try
		{
			out = new PrintWriter(socket.getOutputStream());
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
	
	synchronized public void finishThread()
	{
		finished = true;
	}
	
	synchronized private boolean finished()
	{
		return finished;
	}
	
	private void loop() throws IOException, InterruptedException
	{
		while(!finished()) //Behind a function to ensure thread safety
		{
			if(messagesToSend())
			{
				out.println(getMessageForSending());
			}
			
			if(in.ready())
			{
				addMessageForReceiving(in.readLine());
			}
			
			Thread.sleep(100);
		}
		
		cleanUp();
	}
	
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
	
	synchronized public boolean messagesReceived()
	{
		return inMessages.size() > 0;
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
		
		while((input = in.readLine()) != null)
		{
			try
			{
				GameAndUserData data = gson.fromJson(input, GameAndUserData.class);
				System.out.println(data.nickname + " connected. Wishes to play: " + data.gamemode);
				player.setData(data);
			}
			catch(Exception e)
			{
				out.println("Invalid JSON: " + e.getMessage());
			}
		}
		
		System.out.println("Handshake'd with " + player.getName());
		
	}
}
