import java.util.ArrayList;
import java.util.Dictionary;


public class PlayerPool
{
	private ArrayList<Player> playersSearchingForGame;
	private ArrayList<Player> playersIdle;
	private ArrayList<Player> playersInGame;
	private Dictionary<Player, PlayerState> players; 
	
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
	}
	          
	
	public synchronized void newPlayer(Player player)
	{
		players.put(player, PlayerState.IDLE);
		playersIdle.add(player);
	}
	
	public synchronized void playerLeftGame(Player player)
	{
		playersInGame.remove(player);
		players.put(player, PlayerState.IDLE);
		playersIdle.add(player);
	}
	
	public synchronized void playerSearching(Player player)
	{
		playersIdle.remove(player);
		playersSearchingForGame.add(player);
		players.put(player, PlayerState.SEARCHING);
		playersWaiting = true;
	}
	
	public synchronized Player getWaitingPlayer()
	{
		Player player = playersSearchingForGame.get(0);
		playerPlaying(player);
		
		if(playersSearchingForGame.size() == 0)
		{
			playersWaiting = false;
		}
		
		return player;
	}
	
	public synchronized void playerPlaying(Player player)
	{
		playersSearchingForGame.remove(player);
		playersInGame.add(player);
		players.put(player, PlayerState.PLAYING);
	}
	
}
