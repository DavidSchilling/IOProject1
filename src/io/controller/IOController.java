package io.controller;

import io.model.Game;
import io.view.IOFrame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class IOController
{
	/**
	 * An IO frame for the GUI portion of the project.
	 */
	private IOFrame appFrame;

	/**
	 * All games that can be retrieved from the save file.
	 */
	private ArrayList<Game> projectGames;

	/**
	 * Just a constructor.
	 */
	public IOController()
	{

	}

	/**
	 * @return projectGames
	 */
	public ArrayList<Game> getProjectGames()
	{
		return projectGames;
	}

	/**
	 * This is where the real code starts.
	 */
	public void start()
	{
		appFrame = new IOFrame(this);
	}

	/**
	 * 
	 * @return: Returns a Game, but it could be null, make sure you check.
	 */
	public Game readGameInfo()
	{
		String fileName = "saveFile.txt"; // Without a directory, it will look
											// to the project's directory.
		File currentSaveFile = new File(fileName);

		/*
		 * Major Scanner Methods: .next() returns next string separated around
		 * white space. .nextLine() returns the next line. .nextInt() returns
		 * the next integer value. .nextBoolean() returns the next boolean
		 * value. .nextDouble() returns the next double value.
		 */
		Scanner fileReader;

		int gameRanking = 0;
		String gameTitle = "";
		ArrayList<String> gameRules = new ArrayList<String>();
		Game currentGame = null;

		try
		{
			fileReader = new Scanner(currentSaveFile);
			gameTitle = fileReader.nextLine();
			gameRanking = fileReader.nextInt();
			while (fileReader.hasNext())
			{
				gameRules.add(fileReader.nextLine());
			}

			currentGame = new Game(gameRules, gameRanking, gameTitle);
			fileReader.close();
		}

		catch (FileNotFoundException Ralph)
		{
			JOptionPane.showMessageDialog(appFrame, "File not found.");
			JOptionPane.showMessageDialog(appFrame, Ralph.getMessage());
		}

		return currentGame;
	}

	/**
	 * 
	 * @param toBeParsed A String that will be changed into an integer.
	 * @return If the toBeParsed isn't an integer, it returns a boolean that is false.
	 */
	private boolean checkNumberFormat(String toBeParsed)
	{
		boolean isNumber = false;
		try
		{
			int valid = Integer.parseInt(toBeParsed);
			isNumber = true;
		}
		catch (NumberFormatException Robert)
		{
			JOptionPane.showMessageDialog(appFrame, "Not a valid number.");
		}

		return isNumber;
	}

	/**
	 * Reads the file from the save file.
	 * @return currentGame.
	 */
	public Game readGameInformation()
	{
		Game currentGame = null;
		String fileName = "saveFile.txt";
		File currentSaveFile = new File(fileName);
		Scanner fileReader;
		int gameRanking = 0;
		String gameTitle = "";
		ArrayList<String> gameRules = new ArrayList<String>();

		try
		{
			fileReader = new Scanner(currentSaveFile);
			gameTitle = fileReader.nextLine();
			gameRanking = fileReader.nextInt();
			while (fileReader.hasNext())
			{
				gameRules.add(fileReader.nextLine());
			}

			currentGame = new Game(gameRules, gameRanking, gameTitle);
			fileReader.close();
		}
		catch (FileNotFoundException noFileExists)
		{
			JOptionPane.showMessageDialog(appFrame, noFileExists.getMessage());
		}

		return currentGame;
	}

	/**
	 * Reads all the game information.
	 * @return fileContents
	 */
	public String readAllGameInformation()
	{
		String fileContents = "";
		String fileName = "saveFile.txt";
		File currentSaveFile = new File(fileName);
		Scanner fileReader;

		try
		{
			fileReader = new Scanner(currentSaveFile);
			while (fileReader.hasNext())
			{
				fileContents += fileReader.nextLine();
			}
			fileReader.close();
		}
		catch (FileNotFoundException fileDoesNotExist)
		{
			JOptionPane.showMessageDialog(appFrame, fileDoesNotExist.getMessage());
		}

		return fileContents;
	}

	/**
	 * Converts the save file to a game.
	 * @param currentInfo
	 */
	private void convertTextToGames(String currentInfo)
	{
		projectGames = new ArrayList<Game>();
		String[] gameChunks = currentInfo.split(";");
		for (String currentBlock : gameChunks)
		{
			int currentIndex = currentBlock.indexOf("\n");
			String title = currentBlock.substring(0, currentIndex);

			int nextIndex = currentBlock.indexOf("\n", currentIndex);
			String ranking = currentBlock.substring(currentIndex + 1, nextIndex);
			
			String rules = currentBlock.substring(nextIndex + 1);
			
			Game currentGame = makeGameFromInput(title, ranking, rules);
			
			projectGames.add(currentGame);
		}
	}

	public Game pickRandomGameFromFile()
	{
		Game currentGame = null;

		String allInfo = readAllGameInformation();

		return currentGame;
	}

	/**
	 * 
	 * @param title
	 * @param ranking
	 * @param rules
	 * @return
	 */
	public Game makeGameFromInput(String title, String ranking, String rules)
	{
		Game currentGame = new Game();

		currentGame.setGameTitle(title);

		if (checkNumberFormat(ranking))
		{
			currentGame.setFunRanking(Integer.parseInt(ranking));
		}
		else
		{
			return null;
		}

		String[] temp = rules.split("\n");
		ArrayList<String> tempRules = new ArrayList<String>();

		for (String tempWord : temp)
		{
			tempRules.add(tempWord);
		}
		currentGame.setGameRules(tempRules);

		return currentGame;

	}

	/**
	 * 
	 * @param currentGame
	 */
	public void saveGameInformation(Game currentGame)
	{
		PrintWriter gameWriter;
		String saveFile = "saveFile.txt";

		try
		{
			gameWriter = new PrintWriter(saveFile);

			gameWriter.append(currentGame.getGameTitle() + "\r\n");
			gameWriter.append(Integer.toString(currentGame.getFunRanking()) + "\r\n");
			for (int count = 0; count < currentGame.getGameRules().size(); count++)
			{
				gameWriter.append(currentGame.getGameRules().get(count) + "\r\n");
			}

			gameWriter.append(";" + "\r\n"); // Allows us to later split the game around
										// that character.

			gameWriter.close();
		}
		catch (FileNotFoundException noFileExists)
		{
			JOptionPane.showMessageDialog(appFrame, "Could not Create the save file.");
			JOptionPane.showMessageDialog(appFrame, noFileExists.getMessage());
		}
	}

}
