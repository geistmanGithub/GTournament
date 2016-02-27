package com.geistman.gtournament;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Game implements Parcelable{
    private static final String TAG = "GAME";
    private String player1;
    private String player2;

    private String winner;

    protected Game(Parcel in) {
        player1 = in.readString();
        player2 = in.readString();
        winner = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public Game() {

    }

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
        Log.d(TAG, this.toString());
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
        Log.d(TAG, "Winner set to: "+this.winner);
    }

    public boolean isGameReady() {

        return player1 != null && player2 != null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Game{" +
                "player1='" + player1 + '\'' +
                ", player2='" + player2 + '\'' +
                ", winner='" + winner + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getPlayer1());
        dest.writeString(getPlayer2());
        dest.writeString(getWinner());
    }
}
