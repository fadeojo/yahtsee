package com.carleton;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.Scanner;

public class Player2 {

    private int playerNo, currentRound;
    private String playerName;
    private Dice[] currentRoll = new Dice[5];
    private int numRolls = 0;
    Scanner scan;
    InetAddress host = InetAddress.getLocalHost();
    Socket socket = new Socket(host.getHostName(), 9876);
    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

    public ScoreSheet myScoreSheet;

    public Player2() throws IOException {

        this.currentRound = 1;
        makeDice();
        rollDice(new byte[] { 1, 1, 1, 1, 1 });
        scan = new Scanner(System.in);
    }

    public Player2(int num, String name) throws IOException {
        this.setPlayerName(name);
        this.setPlayerNo(1);
        this.currentRound = 1;
        myScoreSheet = new ScoreSheet(playerName, num);
        makeDice();
        rollDice(new byte[] { 1, 1, 1, 1, 1 });
        scan = new Scanner(System.in);
    }

    public Player2(int num, String name, int t) throws IOException {
        this.setPlayerName(name);
        this.setPlayerNo(1);
        this.currentRound = 1;
        myScoreSheet = new ScoreSheet(playerName, num);
        makeDice();
        rollDice(new byte[] { 1, 1, 1, 1, 1 });
        scan = new Scanner(System.in);
    }

    public ScoreSheet getScoreSheet() {
        return myScoreSheet;
    }

    public void consoleGUI() throws IOException, ClassNotFoundException {

        waitaForServer(socket);
//		oos.close();
//		ois.close();
//		socket.close();

        System.out.println("welcome please enter your name: ");
        String string = scan.next();
        this.setPlayerName(string);
        this.setPlayerNo(1);
        myScoreSheet = new ScoreSheet(this.playerName, this.playerNo);
        myScoreSheet.print();
        while (true) {

            menuList();
            int i = scan.nextInt();
            entreeOptions(i);
        }

    }

    private void waitaForServer(Socket socket) throws IOException, ClassNotFoundException {
        while (true) {
            YahzeeGameState message = (YahzeeGameState) ois.readObject();
            if (message.getCurrentPlayer().equalsIgnoreCase("Player2"))
                break;
        }
    }

    @SuppressWarnings("null")
    private void entreeOptions(int i) throws ClassNotFoundException, IOException {
        if (i == 1 && numRolls < 3) {
            String str = "";
            System.out.println(
                    "Please enter in the dice position that you want to hold. Please separate them with a <<<Spaces>>>");
            while (str.isBlank()) {
                str = scan.nextLine();
            }
            byte[] b = new byte[] { 1, 1, 1, 1, 1 };
            for (int k = 0; k < str.length(); k++) {
                if (str.charAt(k) != ' ') {
                    b[Character.getNumericValue(str.charAt(k))] = 0;
                }
            }
            rollDice(b);
            // System.out.println("here");
        } else if (i == 2 && numRolls < 3) {
            rollDice(new byte[] { 1, 1, 1, 1, 1 });
        } else if (i == 3 || numRolls >= 3) {
            System.out.println("What category woud you like to score this against?");
            int j = scan.nextInt();
            setScoreSheet(j);
            this.numRolls = 0;
            myScoreSheet.print();

//			oos.reset();
//			ois.reset();
//			oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending game state!");
            YahzeeGameState testMessage = new YahzeeGameState("player2", this.myScoreSheet, null, null);
            oos.writeObject(testMessage);
            waitaForServer(socket);



            rollDice(new byte[] { 1, 1, 1, 1, 1 });
        } else {
            System.out.println("Invalid entree. Please enter a valid option");
        }
    }

    public void menuList() {
        System.out.print(getPlayerName() + ", Player " + getPlayerNo() + " Rolled: ");
        printHand();
        System.out.println("What action would you like to perform next?");
        System.out.println("(1) Select dice to hold, and then re-roll the other dice?");
        System.out.println("(2) Re-roll all the dice?");
        System.out.println("(3) Score this round?");
    }

    public void makeDice() {
        Dice dice1 = new Dice(1);
        Dice dice2 = new Dice(2);
        Dice dice3 = new Dice(3);
        Dice dice4 = new Dice(4);
        Dice dice5 = new Dice(5);

        currentRoll[0] = dice1;
        currentRoll[1] = dice2;
        currentRoll[2] = dice3;
        currentRoll[3] = dice4;
        currentRoll[4] = dice5;

    }

    public int rollDice(byte[] toBeRolled) {
        if (numRolls >= 3) {
            return -1;
        }
        for (int i = 0; i < toBeRolled.length; i++) {
            if (toBeRolled[i] == 1) {
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
        if (currentRound < 13) {
            this.currentRound += 1;
        } else {
            System.out.println("sheet full");
        }

    }

    public void setScoreSheet(int category) {

        int score = 0;
        if (category <= 6) {
            for (int i = 0; i < currentRoll.length; i++) {
                if (currentRoll[i].getFace() == category) {
                    score += category;
                }
            }
        } else {
            switch (category) {
                case 7:
                    score = calculateScore.CalculateOfAKind(currentRoll, 3);
                    break;
                case 8:
                    score = calculateScore.CalculateOfAKind(currentRoll, 4);
                    break;
                case 9:
                    score = calculateScore.CalculateFullHouse(currentRoll);
                    break;
                case 10:
                    score = calculateScore.CalculateSmallStraight(currentRoll);
                    break;
                case 11:
                    score = calculateScore.CalculateLargeStraight(currentRoll);
                    break;
                case 12:
                    score = calculateScore.CalculateYahtzee(currentRoll);
                    break;
                case 13:
                    score = calculateScore.AddUpChance(currentRoll);
                    break;
                default:
                    break;
            }
        }

        myScoreSheet.setScoring(category - 1, score);

    }

    public Dice[] getCurrentRoll() {
        return currentRoll;
    }

    public void setCurrentRoll(Dice[] currentRoll) {
        for (int i = 0; i < currentRoll.length; i++) {
            this.currentRoll[i] = currentRoll[i];
        }
    }

    public void printHand() {
        for (int i = 0; i < currentRoll.length; i++) {
            System.out.print("| " + currentRoll[i].getFace() + " |");
        }
        System.out.println("" + "" + "");
    }

    public int getNumRolls() {
        return numRolls;
    }

    public void setNumRolls(int rolls) {

        this.numRolls = rolls;
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Player2 p1 = new Player2();
        p1.consoleGUI();
    }

}
