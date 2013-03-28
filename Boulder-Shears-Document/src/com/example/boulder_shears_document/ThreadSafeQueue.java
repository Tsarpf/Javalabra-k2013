package com.example.boulder_shears_document;

import java.util.ArrayList;

public class ThreadSafeQueue
{
	ArrayList<Object> queue;
	
	public ThreadSafeQueue()
	{
		queue = new ArrayList<Object>();
	}
	
	public synchronized void enqueue(Object o)
	{
		queue.add(o);
	}
	
	public synchronized Object dequeue()
	{
		if(queue.size() == 0)
		{
			return null;
		}
		
		return queue.remove(0);
	}
}
