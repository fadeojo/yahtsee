package com.carleton;

import java.io.IOException;
import java.util.Scanner;

public class working3players {


	private Player1[] players;
	private int numRounds;
	 Scanner scan;
	public working3players(Player1 p1,Player1 p2,Player1 p3) {
		players=new Player1[3];
		players[0]=p1;
		players[1]=p2;
		players[2]=p3;
		numRounds=0;
		scan = new Scanner(System.in);


	}
	public void play() {
		for (int i = 0; i < players.length; i++) {
			players[i].getScoreSheet().print();
		}
		while (numRounds<13*3) {
			if (players[0].getCurrentRound()<=players[2].getCurrentRound()) {
				menuList(players[0]);
				int i=scan.nextInt();
				entreeOptions(players[0], i);
			}else if (players[1].getCurrentRound()<players[0].getCurrentRound()) {
				menuList(players[1]);
				int i=scan.nextInt();
				entreeOptions(players[1], i);
			}else if (players[2].getCurrentRound()<players[1].getCurrentRound()) {
				menuList(players[2]);
				int i=scan.nextInt();
				entreeOptions(players[2], i);
			}
		}


	}
	private void entreeOptions(Player1 p,int i) {
		if (i==1&&p.getNumRolls()<3) {
			String str="";
			System.out.println("Please enter in the dice position that you want to hold. Please separate them with a <<<Spaces>>>");
			while (str.isBlank()) {
				str=scan.nextLine();
			}
			System.out.println(str);
			byte[] b=new byte[] {1,1,1,1,1};
			for (int k = 0; k < str.length(); k++) {
				if (str.charAt(k)!=' ') {
					b[Character.getNumericValue(str.charAt(k))]=0;
				}
			}
			p.rollDice(b);
			//System.out.println("here");
		}else if (i==2&&p.getNumRolls()<3) {
			p.rollDice(new byte[] {1,1,1,1,1});
		}else if (i==3||p.getNumRolls() >= 3) {
			System.out.println("What category woud you like to score this against?");
			int j=scan.nextInt();
			p.setScoreSheet(j);
			numRounds++;
			System.out.println("player "+p.getPlayerNo()+" has completed their turn");
			p.setNumRolls(0);
		}else {
			System.out.println("Invalid entree. Please enter a valid option \n");
		}
	}
	public void menuList(Player1 p) {
		System.out.print(p.getPlayerName()+", Player "+p.getPlayerNo()+" Rolled: ");
		p.printHand();
		System.out.println("What action would you like to perform next?");
		System.out.println("(1) Select dice to hold, and then re-roll the other dice?");
		System.out.println("(2) Re-roll all the dice?");
		System.out.println("(3) Score this round?");
	}

	public static void main(String[] args) throws IOException {

		working3players game=new working3players(new Player1(1,"koro"), new Player1(2,"tayo"),new Player1(3,"bammy"));
		game.play();
	}

}
