package com.carleton;

import java.io.Serializable;


public class ScoreSheet implements Serializable{
	private int playerNo;
	private String playerName;
	private int[] scoring;
	private int totalScore;
	public  ScoreSheet( String name, int playerNo) {
		this.setPlayerNo(playerNo);
		this.setPlayerName(name);
		scoring=new int[13];
		for (int i = 0; i < 13; i++) {
			scoring[i]=-1;
		}
	}


	public int getScore(int i) {
		return scoring[i-1];
	}

	public void setScoring(int position, int score) {
		scoring[position]=score;
	}
	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public void print() {
		System.out.println("Name: " +this.playerName+" | Current Score: "+totalScore+" | currentRound: ");

		System.out.println("(1) Ones: "+scoring[0]);
		System.out.println("(2) Twos: "+scoring[1]);
		System.out.println("(3) Threes: "+scoring[2]);
		System.out.println("(4) Fours: "+scoring[3]);
		System.out.println("(5) Fives: "+scoring[4]);
		System.out.println("(6) Sixes: "+scoring[5]);
		System.out.println("(7) Three of a kind: "+scoring[6]);
		System.out.println("(8) Four of a kind: "+scoring[7]);
		System.out.println("(9) Full House: "+scoring[8]);
		System.out.println("(10) A small straight: "+scoring[9]);
		System.out.println("(11) A large straigth: "+scoring[10]);
		System.out.println("(12) Chance: "+scoring[11]);
		System.out.println("(13) Yahtzee: "+scoring[12]);
		System.out.println("	"
				+ "	"
				+ "");
	}



	public boolean isFull() {
		for (int i = 0; i < scoring.length; i++) {
			if (scoring[i]==-1) {
				return false;
			}
		}
		return true;
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





}
