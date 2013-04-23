import java.net.*;
import java.io.*;

public class ClientHandlerThread extends Thread
{
	private Socket socket = null;
	private PrintWriter out;
	private BufferedReader in;
	private Protocol currentProtocol;
	
	public ClientHandlerThread(Socket socket)
	{
		super("ClientHandlerThread");
		this.socket = socket;
		currentProtocol = new HandshakeProtocol();
	}
	
	public void run()
	{
		try
		{
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			loop();
			
		}
		catch(IOException e)
		{
			
		}
	}
	
	private void loop() throws IOException
	{
		String inputLine = "";
		String outputLine = "";
		
		
		while((inputLine = in.readLine()) != null)
		{
			outputLine = currentProtocol.processInput(inputLine);
			out.println(outputLine);
			
			handleStates();
		}
	}
	
	private void handleStates() throws IOException
	{
		if(currentProtocol.state == Protocol.State.FAILED)
		{
			//If some protocol fails, let's just start from the beginning.
			currentProtocol = new HandshakeProtocol();
		}
		else if(currentProtocol.state == Protocol.State.SUCCEEDED && currentProtocol.type == Protocol.ProtocolType.HANDSHAKE)
		{
			currentProtocol = new BoulderShearsDocumentProtocol();
		}
		else if(currentProtocol.state == Protocol.State.SUCCEEDED && currentProtocol.type == Protocol.ProtocolType.GAME)
		{
			//Game complete, but players did not wish for a new player or to exit
		}
		else if(currentProtocol.state == Protocol.State.FINISHED && currentProtocol.type == Protocol.ProtocolType.GAME)
		{
			//Game complete and at least one player wishes for a new opponent
		}
		else if(currentProtocol.state == Protocol.State.EXITING)
		{
			cleanUp();
		}
	}
	
	private void cleanUp() throws IOException
	{
	    out.close();
	    in.close();
	    socket.close();
	}
}























