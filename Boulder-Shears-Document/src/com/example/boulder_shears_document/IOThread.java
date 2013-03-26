package com.example.boulder_shears_document;

import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class IOThread extends HandlerThread
{
	IOHandler handler;
	SocketClient client;
	
	public IOThread() 
	{
		super("IO Worker");
		Log.w("debug", "Creating IOThread");
	}
	
	public void sendMessageToThreadFromOtherThread(Object message)
	{
		Message msg = handler.obtainMessage();
		
		msg.obj = message;
		
		handler.sendMessage(msg);
	}
	
	@Override
	public void run()
	{
		Log.w("debug", "running IOThread");

		try
		{
			Log.w("debug", "going to prepare...");
			Looper.prepare();
			Log.w("debug", "...prepared");
			
			//10.0.2.2 is the IP for desktop to which android is connected via USB
			client = new SocketClient("datisbox.net", 6666);
			Log.w("debug", "created client");
			handler = new IOHandler(client);
			Log.w("debug", "created handler");
			
			Looper.loop();
			Log.w("debug", "looping");
		}
		catch(Exception e)
		{
			Log.w("debug","thread run problems: " + e.getMessage());
		}
		Log.w("debug", "got through thread run function");
	}
}
