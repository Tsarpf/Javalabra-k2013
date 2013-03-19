
public class BoulderShearsDocumentProtocol extends Protocol
{
	GameData data;
	
	public BoulderShearsDocumentProtocol()
	{
		super();
	}
	
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
		case END:
			state = State.SUCCESS;
			option = "3 - end";
			break;
		default:
			state = State.FAILED;
			return "FAILED. You broke the client :(";
		}
		
		return returnString + option;
	}
	
	class GameData
	{
		String playerName;
		GameMove move;
	}
	
	enum GameMove
	{
		BOULDER,
		SHEARS,
		DOCUMENT,
		END
	}
}
