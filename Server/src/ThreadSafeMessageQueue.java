import java.util.ArrayList;


public class ThreadSafeMessageQueue
{
	ArrayList<String> queue;
	
	public ThreadSafeMessageQueue()
	{
		queue = new ArrayList<String>();
	}
	
	public synchronized void enqueue(String msg)
	{
		queue.add(msg);
	}
	
	public synchronized String dequeue()
	{
		if(queue.size() == 0)
		{
			return null;
		}
		
		return queue.remove(0);
	}
}
