package Server;
import java.util.ArrayList;

/**
 * Thread that continuously checks player pool for possibilities to start new games between players.
 * @author Tsarpf
 *
 */
public class MatchMakingThread extends Thread
{
	/**
	 * Main PlayerPool used throughout the server
	 */
	PlayerPool pool;
	
	//TODO: add support for different games
	//TODO: Create separate games handler and separate game winner determiner or something.
	
	ArrayList<Player> playersForNextBoulderShearsDocumentGame;
	
	/**
	 * 
	 * @param pool Main PlayerPool used throughout the server
	 */
	public MatchMakingThread(PlayerPool pool)
	{
		this.pool = pool;
		
		playersForNextBoulderShearsDocumentGame = new ArrayList<Player>();
	}
	
	@Override
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
			
			Player player = pool.getFirstSearchingPlayer();
			
			System.out.println("Player '" + player.getName() + "' was found by MatchMaking thread...");
			
			//TODO: player class should contain information about what game player specifically wants to play.
			
			playersForNextBoulderShearsDocumentGame.add(player);
			
			if(playersForNextBoulderShearsDocumentGame.size() == 2)
			{
				//TODO: Make this less ugly. 
				new BoulderShearsDocumentGame(playersForNextBoulderShearsDocumentGame.get(0),
						playersForNextBoulderShearsDocumentGame.get(1), pool).start();
				playersForNextBoulderShearsDocumentGame.clear();
			}
			
			
		}
	}
}









