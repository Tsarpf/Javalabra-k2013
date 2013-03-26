import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.*;

public class HandshakeProtocolTest
{

	@Test
	public void processInputReturnsPingOnHelloTest()
	{
		HandshakeProtocol protocol = new HandshakeProtocol();
		String ping = protocol.processInput("HELLO");
		assertTrue("Should return a valid ping message on HELLO",
				ping.startsWith("PING"));
	}
	
	@Test
	public void processInputPingPongTestWorksTest()
	{
		HandshakeProtocol protocol = new HandshakeProtocol();
		String ping = protocol.processInput("HELLO");
		String pong = "PONG" + ping.substring(4);
		System.out.println(ping);
		System.out.println(pong);
		
		assertTrue("After correct pong, processInput should acknowledge it succeeded",
				protocol.processInput(pong).startsWith("SUCCESS"));
	}
	
	@Test
	public void protocolStateCorrectAfterHelloTest()
	{
		HandshakeProtocol protocol = new HandshakeProtocol();
		
		protocol.processInput("HELLO");
		
		assertTrue("Wrong state after hello",
				protocol.state == Protocol.State.WAITINGFORPONG);
		
	}
	
	@Test
	public void protocolStateCorrectAfterPongTest()
	{
		HandshakeProtocol protocol = new HandshakeProtocol();
		String ping = protocol.processInput("HELLO");
		String pong = "PONG" + ping.substring(4);
		
		protocol.processInput(pong);
		
		assertTrue("Wrong state after pong",
				protocol.state == Protocol.State.WAITINGFORDETAILS);
	}

	
	@Test
	public void processInputReturnsSuccesAfterProcessInputWasGameAndUserDataJSONTest()
	{
		HandshakeProtocol protocol = new HandshakeProtocol();
		
		HandshakeProtocol.GameAndUserData GAUD = protocol.new GameAndUserData();
		
		GAUD.nickname = "TestNickname";
		GAUD.gamemode = "BoulderShearsDocument";
		
		Gson gson = new Gson();
		String json = gson.toJson(GAUD);
		
		System.out.println("JSON: " + json);
		
		String rString = protocol.processInput(json);
		
		assertTrue("Should succeed on valid json",
				rString.startsWith("SUCCESS"));
	}
	
	@Test
	public void dataCorrectAfterProcessInputJSONConversionsTest()
	{
		HandshakeProtocol protocol = new HandshakeProtocol();
		
		HandshakeProtocol.GameAndUserData GAUD = protocol.new GameAndUserData();
		
		GAUD.nickname = "TestNickname";
		GAUD.gamemode = "BoulderShearsDocument";
		
		Gson gson = new Gson();
		String json = gson.toJson(GAUD);
		
		String rString = protocol.processInput(json);
		
		System.out.println(GAUD.gamemode + " should be same as: " + protocol.data.gamemode);
		System.out.println(GAUD.nickname + " should be same as: " + protocol.data.nickname);
		
		assertTrue("JSON conversion messed up data",
				protocol.data.gamemode.equals(GAUD.gamemode) && protocol.data.nickname.equals(GAUD.nickname));
	}
}
