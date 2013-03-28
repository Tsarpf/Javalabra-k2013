import java.util.Random;


public class HandshakeProtocol extends Protocol
{
	
	
	int ping;
	GameAndUserData data;
	
	public HandshakeProtocol()
	{
		super();
		this.type = ProtocolType.HANDSHAKE;
		ping = new Random().nextInt();
	}
	
	@Override
	public String processInput(String input)
	{
		System.out.println(input);
		if(input.startsWith("HELLO"))
		{
			System.out.println("Got hello, sending ping");
			
			state = State.WAITINGFORPONG;
			return "PING" + ping;
		}
		else if(input.startsWith("PONG"))
		{
			System.out.println("Got pong");
			
			input = input.substring(4); //Cuts out the PONG part
			int pong = Integer.parseInt(input);
			
			if(pong == ping)
			{
				state = State.WAITINGFORDETAILS;
				return "SUCCESS. Now give gamemode etc.";
			}
			else
			{
				state = State.FAILED;
				return "FAILED. Invalid pong";
			}
		}
		else //_should_ be in JSON if we got here
		{
			try
			{
				data = gson.fromJson(input, GameAndUserData.class);
				System.out.println(data.nickname + " " + data.gamemode);
				return "SUCCESS. Game beginning";
			}
			catch(Exception e)
			{
				System.out.println("Convert to JSON failed: " +  e.getMessage());
				state = State.FAILED;
				return "FAILED. Invalid JSON";
			}
		}
	}
	
	public class GameAndUserData
	{
		String nickname;
		String gamemode;
	}
}