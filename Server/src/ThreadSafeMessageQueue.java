import java.util.ArrayList;


public class ThreadSafeMessageQueue
{
	ArrayList<Object> queue;
	
	public ThreadSafeMessageQueue()
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
