import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class PlayerIOThread extends Thread
{
	Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	
	private ArrayList<String> outMessages;
	private ArrayList<String> inMessages;
	
	public PlayerIOThread(Socket socket)
	{
		super("ClientHandlerThread");
		this.socket = socket;
		
		outMessages = new ArrayList<String>();
		inMessages = new ArrayList<String>();
	}
	
	public void run()
	{
		try
		{
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			handShake();
		}
		catch(IOException e)
		{
			
		}
	}
	
	private void handShake()
	{
		
	}
	
	private void loop()
	{
		
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
