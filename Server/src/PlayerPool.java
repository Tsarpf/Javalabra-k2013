import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains players and handles adding them to the correct lists for easier access by matchmakers etc.
 * @author Tsarpf
 *
 */
public class PlayerPool
{
	private ArrayList<Player> playersSearchingForGame; //Self explanatory
	private ArrayList<Player> playersIdle;
	private ArrayList<Player> playersInGame;
	
	/**
	 * Could be used for creating a pretty lobby view for the client so they can see who else is "logged in"
	 */
	private Map<Player, PlayerState> players; 
	
	enum PlayerState
	{
		PLAYING,
		IDLE,
		SEARCHING
	}
	
	boolean playersWaiting;
	
	public PlayerPool()
	{
		playersSearchingForGame = new ArrayList<Player>();
		playersIdle = new ArrayList<Player>();
		playersInGame = new ArrayList<Player>();
		players = new HashMap<Player, PlayerState>();
	}
	       
	
	/**
	 * Adds a completely new player to the pool
	 * @param player Player in question
	 */
	public synchronized void newPlayer(Player player)
	{
		System.out.println("Adding new player '" + player.getName() + "' to pool");
		players.put(player, PlayerState.IDLE);
		playersIdle.add(player);
		
		//TODO: lobby implementation. The following is just a temporary solution:
		
		playerSearching(player);
	}
	/**
	 * Player was playing and has now left a game. Should always be used when a game ends.
	 * @param player Player in question
	 */
	public synchronized void playerLeftGame(Player player)
	{
		playersInGame.remove(player);
		players.put(player, PlayerState.IDLE);
		playersIdle.add(player);
	}
	
	/**
	 * Player was idling in the lobby and is now searching for a game. 
	 * @param player The player in question
	 */
	public synchronized void playerSearching(Player player)
	{
		playersIdle.remove(player);
		playersSearchingForGame.add(player);
		players.put(player, PlayerState.SEARCHING);
		playersWaiting = true;
		
		
	}
	
	/**
	 * Gets the first player from the queue of players waiting for a game.
	 * @return The player in question
	 */
	public synchronized Player getFirstSearchingPlayer()
	{
		Player player = playersSearchingForGame.get(0);
		playerPlaying(player);
		
		if(playersSearchingForGame.size() == 0)
		{
			playersWaiting = false;
		}
		
		return player;
	}
	
	/**
	 * Should always be used when a player starts playing. Handles
	 * @param player Player in question
	 */
	public synchronized void playerPlaying(Player player)
	{
		playersSearchingForGame.remove(player);
		playersInGame.add(player);
		players.put(player, PlayerState.PLAYING);
	}
	
}
