package com.carleton;

import java.io.Serializable;

public class Dice implements Serializable{

	//the number of the dice i.e dice1
	private int diceNo;
	private int face=-1;
	/**
	 * dice constructor
	 * **/
	public  Dice(int diceNo) {
		this.setDiceNo(diceNo);
	}
	/**
	 * returns the face of the dice
	 * **/
	public int roll() {
		face=(int) (10*Math.random());
		while(face>6||face<1) {
			face=(int) (10*Math.random());
		}
		return face;
	}
	/**
	 * returns the dice number
	 * i.e dice1 returns 1
	 * **/
	public int getDiceNo() {
		return diceNo;
	}
	public void setDiceFace(int i) {
		face=i;
	}
	public int getFace() {
		return face;
	}
	public void reset() {
		face=-1;
	}
	/**
	 * takes in an integer and
	 * sets the diceNo to the given integer
	 * **/
	public void setDiceNo(int diceNo) {
		this.diceNo = diceNo;
	}
	public void print() {
		System.out.println(face);
	}

}
