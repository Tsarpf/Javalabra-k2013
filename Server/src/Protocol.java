import com.google.gson.*;

/**
 * Superclass for all protocols to reduce duplicate code needed for every protocol
 * @author Tsarpf
 *
 */
public abstract class Protocol
{

	/**
	 * Is used to describe the current state of the protocol
	 */
	public enum State
	{
		SUCCEEDED,
		EXITING,
		FINISHED,
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
	
	/**
	 * Is used to determine whether protocol is for an actual game or for a handshake.
	 */
	public enum ProtocolType
	{
		HANDSHAKE,
		GAME
	}
	
	
	public ProtocolType type;
	public State state;
	Gson gson;
	
	/**
	 * Used to process input from client and respond as specified by the currently selected protocol.
	 */
	public abstract String processInput(String input);
}