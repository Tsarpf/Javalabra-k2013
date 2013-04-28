package Server;
import CommonData.*;
import com.google.gson.*;

/**
 * Handles communication between the game and a player's IO thread
 * @author Tsarpf
 *
 */
public class BoulderShearsDocumentGameProtocol
{
	Gson gson;
	public BoulderShearsDocumentGameProtocol()
	{
		gson = new Gson();
	}
	
	
	public void sendNewGame(Player player, NewGameData newGame)
	{
		String json = gson.toJson(newGame);
		
		player.sendMessage(json);
	}
	
	public void sendOpponentMadeMove(OpponentMadeMove foSho, Player player)
	{
		String json = gson.toJson(foSho);
		
		player.sendMessage(json);
	}
	
	public void sendGameInfo(Player player, GameInfo info)
	{
		String json = gson.toJson(info);
		
		player.sendMessage(json);
	}
	
	public GameData getPlayerGameMoveData(Player player)
	{
		try
		{
			return gson.fromJson(player.receiveMessage(),GameData.class);
		}
		catch(Exception e)
		{
			System.out.println("Getting player move data failed: " + e.getMessage());
			return null;
		}		
	}
	
}
