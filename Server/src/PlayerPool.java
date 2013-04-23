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
	
	public synchronized void playerLookingForGame(Player player)
	{
		playersIdle.remove(player);
		playersSearchingForGame.add(player);
		players.put(player, PlayerState.SEARCHING);
	}
	
	public synchronized void playerPlaying(Player player)
	{
		playersSearchingForGame.remove(player);
		playersInGame.add(player);
		players.put(player, PlayerState.PLAYING);
	}
	
}
