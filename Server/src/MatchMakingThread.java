
public class MatchMakingThread extends Thread
{
	PlayerPool pool;
	public MatchMakingThread(PlayerPool pool)
	{
		this.pool = pool;
	}
	
	public void run()
	{
		while(true)
		{
			if(!pool.playersWaiting)
			{
				try
				{
					Thread.sleep(500);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			
			
		}
	}
}
