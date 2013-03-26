package com.example.boulder_shears_document;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.example.boulder_shears_document.MESSAGE";
	
	IOThread iothread;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        Log.w("debug", "reached onCreate MainActivity");
        
        setContentView(R.layout.activity_main);
        
    	iothread = new IOThread(); //Starts a new thread which handles all IO over networks
    	iothread.start();

		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	iothread.sendMessageToThreadFromOtherThread("HELLO");

    	//Log.w("thread", "Exception: " + e.getMessage());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void sendMessage(View view)
    {
    	
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
    	
    	EditText editText = (EditText) findViewById(R.id.edit_message);
    	String message = editText.getText().toString();
    	
    	intent.putExtra(EXTRA_MESSAGE, message);
    	
    	startActivity(intent);
    }
    
}
