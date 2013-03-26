package com.example.boulder_shears_document;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class IOHandler extends Handler
{
	SocketClient client;
	public IOHandler (Looper looper)
	{
		super(looper);
		client = new SocketClient("datisbox.net", 6666);
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
