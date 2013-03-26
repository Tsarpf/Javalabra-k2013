package com.example.boulder_shears_document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class SocketClient
{
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	public SocketClient(String host, int port)
	{
		try
		{
			socket = new Socket(host, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(UnknownHostException e)
		{
			System.err.println("Couldn't find host '" + host + "' in port '" + port + "'");
			Log.w("NetworkError", "Couldn't find host '" + host + "' in port '" + port + "'");
		}
		catch(IOException e)
		{
			System.err.println("Couldn't get I/O streams for host");
			Log.w("NetworkError", "Couldn't get I/O streams for host");
		}
	}
	
	public void sendLine(String message)
	{
		out.println(message);
	}
	
	public String getLine()
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
