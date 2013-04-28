import java.util.ArrayList;

import CommonData.*;


public class BoulderShearsDocumentGame extends Thread
{
	//ArrayList<Player> players;
	
	//Map<Player, Integer> scoreDic;
	
	//GameInfo info;
	
	
	
	Player playerOne;
	Player playerTwo;
	
	Player winner;
	
	GameMove playerOneMove;
	GameMove playerTwoMove;
	
	int playerOneScore;
	int playerTwoScore;
	
	NewGameChoice playerOneChoice;
	NewGameChoice playerTwoChoice;
	
	BoulderShearsDocumentGameProtocol gameProtocol;
	
	boolean continuePlaying;
	
	PlayerPool pool;
	
	public BoulderShearsDocumentGame(ArrayList<Player> players, PlayerPool pool)
	{
		System.out.println("Starting new game");
		
		this.pool = pool;
		
		playerOne = players.get(0);
		playerTwo = players.get(1);
		
		playerOneScore = 0;
		playerTwoScore = 0;
		
		gameProtocol = new BoulderShearsDocumentGameProtocol();
		
		winner = null;
		continuePlaying = true;
	}
	
	public void run()
	{
		System.out.println("Started running BSDGame Thread");
		initializeGame();
		
		try
		{
			loop();
		}
		catch(Exception e)
		{
			System.out.println("Game loop crashed between player one: '" + playerOne.getName() +
					"' and player two: '" + playerTwo.getName());
			System.out.println("Exception message: " + e.getMessage());
		}
	}
	
	private void loop() throws InterruptedException
	{
		
		while(true)
		{
			if(!playerOne.messagesReceived() && !playerTwo.messagesReceived())
			{
				Thread.sleep(200);
				continue;
			}
			
			if(playerOne.messagesReceived())
			{
				GameData data = gameProtocol.getPlayerGameMoveData(playerOne);
				
				if(data == null)
				{
					continue;
				}
				
				playerOneMove = data.move;
				
				if(data.choice != NewGameChoice.CONTINUE)
				{
					continuePlaying = false;
				}
				
				playerOneChoice = data.choice;
				
				if(playerTwoMove == null)
				{
					sendOpponentMadeMove(playerTwo);
				}
			}			
			
			if(playerTwo.messagesReceived())
			{
				GameData data = gameProtocol.getPlayerGameMoveData(playerTwo);
				
				if(data == null)
				{
					continue;
				}
				
				playerTwoMove = data.move;
				
				if(data.choice != NewGameChoice.CONTINUE)
				{
					continuePlaying = false;
				}
				
				playerTwoChoice = data.choice;
				
				if(playerOneMove == null)
				{
					sendOpponentMadeMove(playerOne);
				}	
			}
			
			if(playerOneMove != null && playerTwoMove != null)
			{
				determineWinner();
				sendGameInfo(false);
				
				if(!continuePlaying)
				{
					break;
				}
				
				reset();
			}
		} //end while(true)
		
		
		if(playerOneChoice == NewGameChoice.EXIT)
		{
			pool.playerLeftGame(playerOne);
		}
		else if(playerOneChoice == NewGameChoice.CONTINUE || playerOneChoice == NewGameChoice.NEWOPPONENT)
		{
			pool.playerLeftGame(playerOne);
			pool.playerSearching(playerOne);
		}
		
		if(playerTwoChoice == NewGameChoice.EXIT)
		{
			pool.playerLeftGame(playerTwo);
		}
		else if(playerTwoChoice == NewGameChoice.CONTINUE || playerTwoChoice == NewGameChoice.NEWOPPONENT)
		{
			pool.playerLeftGame(playerTwo);
			pool.playerSearching(playerTwo);
		}
		
	}
	
	
	
	private void reset()
	{
		playerOneMove = null;
		playerTwoMove = null;
		winner = null;
	}
	
	private void determineWinner()
	{
		if(playerOneMove == playerTwoMove)
		{
			winner = null;
		}
		
		if(playerOneMove == GameMove.BOULDER && playerTwoMove == GameMove.DOCUMENT)
		{
			winner = playerTwo;
			playerTwoScore++;
		}
		else if(playerOneMove == GameMove.BOULDER && playerTwoMove == GameMove.SHEARS)
		{
			winner = playerOne;
			playerOneScore++;
		}
		else if(playerOneMove == GameMove.DOCUMENT && playerTwoMove == GameMove.BOULDER)
		{
			winner = playerOne;
			playerOneScore++;
		}
		else if(playerOneMove == GameMove.DOCUMENT && playerTwoMove == GameMove.SHEARS)
		{
			winner = playerTwo;
			playerTwoScore++;
		}
		else if(playerOneMove == GameMove.SHEARS && playerTwoMove == GameMove.BOULDER)
		{
			winner = playerTwo;
			playerTwoScore++;
		}
		else if(playerOneMove == GameMove.SHEARS && playerTwoMove == GameMove.DOCUMENT)
		{
			winner = playerOne;
			playerOneScore++;
		}
	}
	
	private void sendOpponentMadeMove(Player player)
	{
		gameProtocol.sendOpponentMadeMove(new OpponentMadeMove(), player);
	}
	
	private void initializeGame()
	{
		System.out.println("Initializing game...");
		sendNewGame();
		sendGameInfo(true);
	}
	
	//TODO: rewrite this whole thing please. It's absolutely disgusting.
	private void sendGameInfo(boolean firstGame)
	{
		if(firstGame)
		{
			GameInfo info = new GameInfo();
			info.opponentMove = null;
			info.result = null;
			info.playerScore = 0;
			info.opponentScore = 0;
			
			gameProtocol.sendGameInfo(playerOne, info);
			gameProtocol.sendGameInfo(playerTwo, info);
		}
		else
		{
			Result result;
			
			if(winner == playerOne)
			{
				result = Result.WIN;
			}
			else if(winner == playerTwo)
			{
				result = Result.LOSE;
			}
			else
			{
				result = Result.DRAW;
			}
			
			GameInfo info = new GameInfo();
			info.opponentMove = playerTwoMove;
			info.result = result;
			info.opponentScore = playerTwoScore;
			info.playerScore = playerOneScore;
			
			gameProtocol.sendGameInfo(playerOne, info);
			
			if(winner == playerTwo)
			{
				result = Result.WIN;
			}
			else if(winner == playerOne)
			{
				result = Result.LOSE;
			}
			else
			{
				result = Result.DRAW;
			}
			
			info.opponentMove = playerOneMove;
			info.result = result;
			info.opponentScore = playerOneScore;
			info.playerScore = playerTwoScore;
			
			gameProtocol.sendGameInfo(playerTwo, info);
			
		}
	}
	
	private void sendNewGame()
	{
		NewGameData newGame = new NewGameData();
		newGame.opponentName = playerTwo.getName();
		gameProtocol.sendNewGame(playerOne, newGame);
		
		NewGameData newGameTwo = new NewGameData();
		newGameTwo.opponentName = playerOne.getName();
		gameProtocol.sendNewGame(playerTwo, newGameTwo);
	}
	

}
