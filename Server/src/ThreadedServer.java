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
			serverSocket = new ServerSocket(6666);
		}
		catch(IOException e)
		{
			System.out.println("Couldn't listen on port 6666");
		}
		
		while(listening)
		{
			Socket socket = serverSocket.accept();
			
			Player player = new Player(socket, pool);
		}
	}

}



/*
import java.net.*;
import java.io.*;

public class KKMultiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        while (listening)
	    new KKMultiServerThread(serverSocket.accept()).start();

        serverSocket.close();
    }
}
*/