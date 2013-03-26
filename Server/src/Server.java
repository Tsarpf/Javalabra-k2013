import java.io.*;
import java.net.*;

public class Server
{
	
	static ServerSocket serverSocket;
	static Socket clientSocket;
	static PrintWriter out;
	static BufferedReader in;
	static String inputLine, outputLine;
	static Protocol currentProtocol;
	
    public static void main(String[] args) throws IOException
    {
    	
    	try
    	{
    		System.out.println("Initializing");
    		initialize(); //Sets up sockets etc
			
			currentProtocol = new HandshakeProtocol();
			
			
			System.out.println("Looping...");
			handleProtocol(); //Loops and handles input&output according to a protocol
			
			if(currentProtocol.state == Protocol.State.FAILED)
			{
				System.out.println(currentProtocol.type.toString() +  " FAILED");
				out.println(currentProtocol.type.toString() +  " FAILED");
			}
			
			System.out.println("Exiting..");
			cleanUp();
			
			
		} 
    	catch (Exception e)
    	{
			e.printStackTrace();
		}
    }
    
    private static void initialize()
    {
    	serverSocket = null;
        try
        {
        	serverSocket = new ServerSocket(6666); //TODO: get port from server config file
        }
        catch (IOException e)
        {
        	System.err.println("Couldn't listen to port 6666"); 
        	e.printStackTrace();
        	System.exit(1);
        }
        
        clientSocket = null;
        
        try
        {
        	System.err.println("waiting for accept");
        	clientSocket = serverSocket.accept(); //Waits until client connects
        }
        catch(IOException e)
        {
        	System.err.println("Accept failed");
        	e.printStackTrace();
        	System.exit(1);
        }
    	System.err.println("through accept");
        
        try
        {
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		}
        catch (IOException e)
        {
			System.err.println("Getting a hold of streams failed");
			e.printStackTrace();
        	System.exit(1);
		}
    }
    
    private static void handleProtocol()
    {
        try
        {
			while((inputLine = in.readLine()) != null)
			{
				//TODO: add a timeout so we can get out if something hangs.
				
				outputLine = currentProtocol.processInput(inputLine);
				
				out.println(outputLine);
				
				if(currentProtocol.state == Protocol.State.FAILED)
				{
					break;
				}
				else if(currentProtocol.state == Protocol.State.SUCCESS && currentProtocol.type == Protocol.ProtocolType.HANDSHAKE)
				{
					currentProtocol = new BoulderShearsDocumentProtocol();
				}
				else if(currentProtocol.state == Protocol.State.SUCCESS)
				{
					//Game complete
					break;
				}
			}
		}
        catch (IOException e)
        {
			e.printStackTrace();
		}
    }
    
    private static void cleanUp()
    {
        try
        {
            out.close();
			in.close();
	        clientSocket.close();
	        serverSocket.close();
		}
        
        catch (IOException e)
        {
			e.printStackTrace();
		}

    }
}