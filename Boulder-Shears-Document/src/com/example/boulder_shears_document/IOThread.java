package com.example.boulder_shears_document;

import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class IOThread
{
	HandlerThread hThread;
	IOHandler handler;
	public IOThread()
	{
		hThread = new HandlerThread("IO Worker");
		Looper looper = hThread.getLooper();
		handler = new IOHandler(looper);
	}
	
	public void sendMessageToThread(Object message)
	{
		Message msg = handler.obtainMessage();
		
		msg.obj = message;
		
		handler.sendMessage(msg);
	}
}
