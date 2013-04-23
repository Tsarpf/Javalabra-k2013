/**
 * Protocol for handling server's side of the game 'Boulder-Shears-Document'
 */
public class BoulderShearsDocumentProtocol extends Protocol
{
	GameData data;
	
	public BoulderShearsDocumentProtocol()
	{
		super();
	}
	
	@Override
	public String processInput(String input)
	{
		try
		{
			data = gson.fromJson(input, GameData.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Failed to convert gamemove thingy to JSON");
			return "FAILED. invalid JSON";
		}
		
		String returnString = "You chose ";
		String option;
		switch(data.move)
		{
		case BOULDER:
			option = "0 - boulder";
			break;
		case SHEARS:
			option = "1 - shears";
			break;
		case DOCUMENT:
			option = "2 - document";
			break;
		case SURRENDER:
			state = State.EXITING;
			option = "3 - end";
			break;
		default:
			state = State.FAILED;
			return "FAILED. You broke the client :(";
		}
		
		return returnString + option;
	}
	
	/**
	 * Contains information about which player did what in his/her turn
	 */
	class GameData
	{
		String playerName;
		GameMove move;
	}

	/**
	 * Describes all possible moves a player could make in the game this protocol is used for
	 */
	enum GameMove
	{
		BOULDER,
		SHEARS,
		DOCUMENT,
		SURRENDER,
		NEWOPPONENT //Player clicked a checkbox or something
	}
}
