
public class Game extends Thread
{
	
	IOThread thread;
	ThreadSafeQueue readQueue, writeQueue;
	
	public Game() 
	{
		readQueue = new ThreadSafeQueue();
		writeQueue = new ThreadSafeQueue();
	}
}
