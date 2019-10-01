package com.carleton;

import java.io.Serializable;

public class YahzeeGameState implements Serializable {

    private static final long serialVersionUID = 5878730180620366043L;
    private String currentPlayer;
    private ScoreSheet player_1;
    private ScoreSheet player_2;
    private ScoreSheet player_3;
    private boolean exit;

    public YahzeeGameState(String player, ScoreSheet p1,  ScoreSheet p2,  ScoreSheet p3 ) {
        this.currentPlayer = player;
        this.player_1 = p1;
        this.player_2 = p2;
        this.player_3 = p3;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }


    public ScoreSheet getPlayer_1() {
        return player_1;
    }

    public void setPlayer_1(ScoreSheet player_1) {
        this.player_1 = player_1;
    }

    public ScoreSheet getPlayer_2() {
        return player_2;
    }

    public void setPlayer_2(ScoreSheet player_2) {
        this.player_2 = player_2;
    }

    public ScoreSheet getPlayer_3() {
        return player_3;
    }

    public void setPlayer_3(ScoreSheet player_3) {
        this.player_3 = player_3;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}

