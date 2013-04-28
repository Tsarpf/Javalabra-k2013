package Server;
import java.util.ArrayList;

/**
 * Thread safe queue often needed for communication between code that is executed in different threads
 * @author Tsarpf
 *
 */
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
