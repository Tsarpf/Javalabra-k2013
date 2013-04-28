import java.util.Scanner;
import CommonData.*;

public class Game extends Thread
{
	
	IOThread thread;
	ThreadSafeQueue readQueue, writeQueue;
	Scanner reader;
	
	public Game() 
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
