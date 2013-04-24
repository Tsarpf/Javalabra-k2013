import java.util.ArrayList;


public class MatchMakingThread extends Thread
{
	PlayerPool pool;
	///ArrayList<Player> playersPlaying;
	//ArrayList<Player> playersSearching;
	
	//TODO: add support for different games
	//ArrayList<BoulderShearsDocumentGame> games;
	
	ArrayList<Player> playersForNextBoulderShearsDocumentGame;
	
	public MatchMakingThread(PlayerPool pool)
	{
		//playersPlaying = new ArrayList<Player>();
		//playersSearching = new ArrayList<Player>();
		//games = new ArrayList<BoulderShearsDocumentGame>();
		
		this.pool = pool;
		
		playersForNextBoulderShearsDocumentGame = new ArrayList<Player>();
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
			
			Player player = pool.getSearchingPlayer();
			
			//TODO: player class should contain information about what game player wants to play.
			
			playersForNextBoulderShearsDocumentGame.add(player);
			
			if(playersForNextBoulderShearsDocumentGame.size() == 2)
			{
				new BoulderShearsDocumentGame(playersForNextBoulderShearsDocumentGame, pool).start();
				playersForNextBoulderShearsDocumentGame.clear();
			}
			
			
		}
	}
}









