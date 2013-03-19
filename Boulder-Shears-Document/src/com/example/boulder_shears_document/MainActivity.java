package com.example.boulder_shears_document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.boulder_shears_document.MESSAGE";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /*
    private void testClient()
    {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try
        {
            echoSocket = new Socket("datisbox.net", 6666);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        }
        catch (UnknownHostException e)
        {
            System.err.println("Don't know about host: datisbox.net.");
            Log.w("server","Don't know about host: datisbox.net.");
            //System.exit(1);
        }
        catch (IOException e)
        {
            System.err.println("Couldn't get I/O for "
                               + "the connection to: datisbox.net.");
            //System.exit(1);
            Log.w("server","Couldn't get I/O for "
                               + "the connection to: datisbox.net.");
        }
        
        try
        {
	        out.println("HELLO");
	        String inLine;
	        while((inLine = in.readLine()) != null)
	        {
	        	if(inLine.startsWith("PING"))
	        	{
	        		String pong = "PONG" + inLine.substring(4);
	        		out.println(pong);	        		
	        	}
	        	
	        	Log.w("server",inLine);
	        }
        }
        catch(IOException e)
        {
        	
        }

        /*
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
	
		try
		{
			while ((userInput = stdIn.readLine()) != null) {
			    out.println(userInput);
			    System.out.println("echo: " + in.readLine());
			}
		
			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
		}
		catch(IOException e)
		{
			
		}
		//* /
    }
    */
    public void sendMessage(View view)
    {
    	//testClient();
    	
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	
    	intent.putExtra(EXTRA_MESSAGE, message);
    	
    	startActivity(intent);
    }
    
}
