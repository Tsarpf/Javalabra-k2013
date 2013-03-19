import com.google.gson.*;

public abstract class Protocol
{

	public enum State
	{
		SUCCESS,
		PENDING,
		FAILED,
		WAITINGFORPONG,
		WAITINGFORDETAILS
	};
	
	public Protocol()
	{
		state = State.PENDING;
		gson = new Gson();
	}
	
	public enum ProtocolType
	{
		HANDSHAKE,
		GAME
	}
	
	
	public ProtocolType type;
	public State state;
	Gson gson;
	public abstract String processInput(String input);
}