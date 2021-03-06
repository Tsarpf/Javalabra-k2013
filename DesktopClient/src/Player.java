import java.util.Scanner;
import CommonData.*;

/**
 * Handles setting up and running everything for a new player (from client side)
 * @author Tsarpf
 *
 */
public class Player extends Thread
{
	
	IOThread thread;
	ThreadSafeQueue readQueue, writeQueue;
	Scanner reader;
	
	/**
	 * Sets up stuff
	 */
	public Player() 
	{
		readQueue = new ThreadSafeQueue();
		writeQueue = new ThreadSafeQueue();
		
		reader = new Scanner(System.in);
		
		writeQueue.enqueue("HELLO");
		
		thread = new IOThread("datisbox.net", 5345, writeQueue, readQueue);
		thread.start();
		
		GameAndUserData data = new GameAndUserData();
		
		data.gamemode = "TestGame";
		
		System.out.println("Nick?");
		
		data.nickname = reader.nextLine();
		
		writeQueue.enqueue(data);
		
		
		loop();
	}
	
	/**
	 * Handles looping for basic command line gameplay for the player.
	 */
	private void loop()
	{
		String input = "";
		
		GameData gameData;
		while(!(input = reader.nextLine()).toLowerCase().equals("exit"))
		{
			gameData = new GameData();
			gameData.choice = NewGameChoice.CONTINUE;
			gameData.move = null;
			
			if(input.toLowerCase().equals("boulder"))
			{
				gameData.move = GameMove.BOULDER;
				System.out.println(gameData.move.toString());
			}
			else if(input.toLowerCase().equals("shears"))
			{
				gameData.move = GameMove.SHEARS;
				System.out.println(gameData.move.toString());
			}
			else if(input.toLowerCase().equals("document"))
			{
				gameData.move = GameMove.DOCUMENT;
				System.out.println(gameData.move.toString());
			}
			
			if(gameData.move != null)
			{
				writeQueue.enqueue(gameData);
			}
		}
	}
}
