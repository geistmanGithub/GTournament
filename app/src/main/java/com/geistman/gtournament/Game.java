package com.geistman.gtournament;

import android.util.Log;

public class Game {
    private static final String TAG = "GAME";
    private String player1;
    private String player2;

    private String winner;

    public String getPlayer1() {
        return player1;
    }

    private void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    private void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void addPlayer(String player) {
        if (getPlayer1()== null) {
            this.setPlayer1(player);

        }
        else {
            if (getPlayer2() == null) {
                this.player2 = player;
            }
        }
        Log.d(TAG, "Player 1: "+player1);
        Log.d(TAG, "Player 2: "+player2);
        Log.d(TAG, "Game ready: "+isGameReady());
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public boolean isGameReady() {
        return player1 != null && player2 != null;
    }
}
