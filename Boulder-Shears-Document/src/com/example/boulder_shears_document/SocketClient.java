package com.example.boulder_shears_document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

/**
 * Handles the socket needed for communication between client and server
 * @author Tsarpf
 *
 */
public class SocketClient
{
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	/**
	 * Initializes and creates the connection
	 */
	public SocketClient(String host, int port)
	{
		Log.w("debug", "Creating SocketClient");
		try
		{
			Log.w("debug", "new socket");
			//socket = new Socket(host, port);
			socket = new Socket();
			socket.connect(new InetSocketAddress(host, port), 5000);
			Log.w("debug", "new writer");
			out = new PrintWriter(socket.getOutputStream(), true);
			Log.w("debug", "new reader");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
		}
		catch(UnknownHostException e)
		{
			Log.w("NetworkError", "Couldn't find host '" + host + "' in port '" + port + "'");
		}
		catch(IOException e)
		{
			Log.w("NetworkError", "Couldn't get I/O streams for host: " + e.getMessage());
		}
		Log.w("debug", "SocketClient made");
	}
	
	/**
	 * Simply pushes a string to the output stream
	 * @param message Message to send in plain text
	 */
	public void sendLine(String message)
	{
		out.println(message);
	}
	
	/**
	 * Reads a line from the input stream. Waits for something to appear in the stream if it is empty
	 */
	public String readLine()
	{
		try
		{
			return in.readLine();
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return "Couldn't read line from stream because: " + e.getMessage();
		}
	}
	
	/**
	 * Indicates whether stream is readable without waiting
	 */
	public boolean readyForRead() throws IOException
	{
		return in.ready();
	}
	
	/**
	 * Cleans up everything by closing sockets and streams
	 */
	public void close()
	{
		try
		{
			in.close();
			out.close();
			socket.close();
		}
		catch(IOException e )
		{
			e.printStackTrace();
			System.err.println("Couldn't close socket and/or streams");
		}
	}
}
