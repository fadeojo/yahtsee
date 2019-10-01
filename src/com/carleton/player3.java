package com.carleton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Scanner;


public class player3 {


	private int playerNo,currentRound;
	private String playerName;
	private  Dice[] currentRoll=new Dice[5];
	private  int numRolls=0;
	Scanner scan;
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendReceiveSocket;
	boolean turnsHandler;

	private clientSideConnection csc;
	private ScoreSheet myScoreSheet;

	public player3() {

		this.currentRound=1;
		makeDice();
		rollDice(new byte[] {1,1,1,1,1});
		scan = new Scanner(System.in);
	}
	public player3(int num, String name) {
		this.setPlayerName(name);
		this.setPlayerNo(2);
		this.currentRound=1;
		myScoreSheet=new ScoreSheet(playerName, num);
		makeDice();
		rollDice(new byte[] {1,1,1,1,1});
		scan = new Scanner(System.in);
	}
	public ScoreSheet getScoreSheet() {
		return myScoreSheet;
	}
	public void consoleGUI() {

			System.out.println("welcome please enter your name: ");
			String string=scan.next();
			this.setPlayerName(string);
			this.setPlayerNo(3);
			myScoreSheet=new ScoreSheet(this.playerName,this.playerNo);
			while (turnsHandler&&currentRound<13) {
				menuList();
				int i=scan.nextInt();
				entreeOptions(i);
			}

	}

		private void entreeOptions(int i) {
			if (i==1&&numRolls<3) {
				String str="";
				System.out.println("Please enter in the dice position that you want to hold. Please separate them with a <<<Spaces>>>");
				while (str.isBlank()) {
					str=scan.nextLine();
				}
				byte[] b=new byte[] {1,1,1,1,1};
				for (int k = 0; k < str.length(); k++) {
					if (str.charAt(k)!=' ') {
						b[Character.getNumericValue(str.charAt(k))]=0;
					}
				}
				rollDice(b);
				//System.out.println("here");
			}else if (i==2&&numRolls<3) {
				rollDice(new byte[] {1,1,1,1,1});
			}else if (i==3||numRolls >= 3) {
				System.out.println("What category woud you like to score this against?");
				int j=scan.nextInt();
				setScoreSheet(j);
				this.numRolls=0;
				myScoreSheet.print();
				turnsHandler=false;
			}else {
				System.out.println("Invalid entree. Please enter a valid option");
			}
		}
		public void connectToServer() {
			csc=new clientSideConnection();
		}
	public void menuList() {
		System.out.print(getPlayerName()+", Player "+getPlayerNo()+" Rolled: ");
		printHand();
		System.out.println("What action would you like to perform next?");
		System.out.println("(1) Select dice to hold, and then re-roll the other dice?");
		System.out.println("(2) Re-roll all the dice?");
		System.out.println("(3) Score this round?");
	}

	public void makeDice() {
		Dice dice1=new Dice(1);
		Dice dice2=new Dice(2);
		Dice dice3=new Dice(3);
		Dice dice4=new Dice(4);
		Dice dice5=new Dice(5);

		currentRoll[0]=dice1;
		currentRoll[1]=dice2;
		currentRoll[2]=dice3;
		currentRoll[3]=dice4;
		currentRoll[4]=dice5;


	}
	public  int rollDice(byte[] toBeRolled) {
		if (numRolls>=3) {
		return -1;
		}	for (int i = 0; i < toBeRolled.length; i++) {
			if (toBeRolled[i]==1) {
				currentRoll[i].roll();
			}
		}

		numRolls++;
		return 1;
	}

	public int getPlayerNo() {
		return playerNo;
	}
	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getCurrentRound() {
		return currentRound;
	}
	public void updateCurrentRound() {
		if (currentRound<13) {
			this.currentRound+=1;
		}else {
			System.out.println("sheet full");
		}

	}

	public void setScoreSheet(int category) {

		int score=0;
		if (category<=6) {
			for (int i = 0; i < currentRoll.length; i++) {
				if (currentRoll[i].getFace()==category) {
					score+=category;
				}
			}
		}else {
			switch (category) {
			case 7:
				score=calculateScore.CalculateOfAKind(currentRoll,3);
				break;
			case 8:
				score=calculateScore.CalculateOfAKind(currentRoll,4);
				break;
			case 9:
				score=calculateScore.CalculateFullHouse(currentRoll);
				break;
			case 10:
				score=calculateScore.CalculateSmallStraight(currentRoll);
				break;
			case 11:
				score=calculateScore.CalculateLargeStraight(currentRoll);
				break;
			case 12:
				score=calculateScore.CalculateYahtzee(currentRoll);
				break;
			case 13:
				score=calculateScore.AddUpChance(currentRoll);
				break;
			default:
				break;
			}
		}


		myScoreSheet.setScoring(category-1, score);

	}
	public Dice[] getCurrentRoll() {
		return currentRoll;
	}
	public void setCurrentRoll(Dice[] currentRoll) {
		for (int i = 0; i < currentRoll.length; i++) {
			this.currentRoll[i]=currentRoll[i];
		}
	}
	public void printHand() {
	  for (int i = 0; i < currentRoll.length; i++) {
		  System.out.print("| "+currentRoll[i].getFace()+" |");
	  }
	  System.out.println(""
	  		+ ""
	  		+ "");
	}
	public int getNumRolls() {
		return numRolls;
	}
	public void setNumRolls(int rolls) {

		this.numRolls = rolls;
	}

	private class clientSideConnection{
		private Socket socket;
		private DataInputStream DataIn;
		private DataOutputStream DataOut;

		public clientSideConnection() {
			System.out.println("---Client---");
			try {
				socket=new Socket("localhost",5000);

				DataIn=new DataInputStream(socket.getInputStream());
				DataOut=new DataOutputStream(socket.getOutputStream());

				turnsHandler=DataIn.readBoolean();
				System.out.println("connected to server as player ");
			} catch (IOException e) {
				System.out.println("IOexception from CSC constructor  ");
			}
		}

	}
	public static void main(String[] args) {
		player3 player3=new player3();
		player3.connectToServer();
		player3.consoleGUI();
	}


}
