package com.example.boulder_shears_document;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class IOHandler extends Handler
{
	SocketClient client;
	public IOHandler (SocketClient client)
	{
		super();
		this.client = client;
		Log.w("debug", "Creating IOHandler");
	}
	
	public void handleMessage(Message msg)
	{
		if(msg.obj instanceof String)
		{
			client.sendLine((String)msg.obj);
		}
		//else if json
		//else error
	}
}
