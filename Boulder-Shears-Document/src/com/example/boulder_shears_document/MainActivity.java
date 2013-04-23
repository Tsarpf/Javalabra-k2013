package com.example.boulder_shears_document;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
/**
 * So called main class of the android game client. Handles initialization
 * @author Tsarpf
 *
 */
public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.boulder_shears_document.MESSAGE";
	

	IOThread ioThread;
	ThreadSafeQueue readQueue, writeQueue;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.activity_main);
     
    	
        
    	readQueue = new ThreadSafeQueue();
    	writeQueue = new ThreadSafeQueue();
    	
    	//writeQueue.enqueue("HELLO");
    	
    	ioThread = new IOThread("tulipallo.datisbox.net", 6666, writeQueue, readQueue);
    	ioThread.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * 
     *	Sends a message to the server using the IOThread's queue 
     */
    public void sendMessage(View view)
    {
    	
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	
    	writeQueue.enqueue(message);
    	
    	String response;
    	
    	while((response = (String)readQueue.dequeue()) == null)
    	{}
    	
    	
    	
    	intent.putExtra(EXTRA_MESSAGE, response);
    	
    	startActivity(intent);
    }
    
}
