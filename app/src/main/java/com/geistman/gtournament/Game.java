package com.geistman.gtournament;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.util.Log;

public class Game implements Parcelable, BaseColumns{

    //gameTime long primary key, player1 text, player2 text, winner text

    private static final String TAG = "GAME";
    private String player1;
        public final static String GAME_PLAYER1 = "player1";
    private String player2;
        public final static String GAME_PLAYER2 = "player2";
    private String winner;
        public final static String GAME_WINNER = "winner";
    private int player1Won;
    private int player2Won;

    protected Game(Parcel in) {
        player1 = in.readString();
        player2 = in.readString();
        winner = in.readString();
        player1Won = in.readInt();
        player2Won = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getPlayer1());
        dest.writeString(getPlayer2());
        dest.writeString(getWinner());
        dest.writeInt(player1Won);
        dest.writeInt(player2Won);
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

    public boolean addPlayer(String player) {
        if (getPlayer1()== null) {
            this.setPlayer1(player);
            Log.d(TAG, this.toString());
            return true;
        }
        else {
            if (getPlayer2() == null) {
                this.setPlayer2(player);
                Log.d(TAG, this.toString());
                return true;
            }
        }
        Log.d(TAG, this.toString());
        return false;
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
                ", player1Won=" + player1Won +
                ", player2='" + player2 + '\'' +
                ", player2Won=" + player2Won +
                ", winner='" + winner + '\'' +
                '}';
    }


    public void confirmWinner() {
        GameHistory.addGame(this);
    }

    public void setWins(int player1Won, int player2Won) {
        this.player1Won = player1Won;
        this.player2Won = player2Won;
    }

    public int getPlayer1Won() {
        return player1Won;
    }

    public int getPlayer2Won() {
        return player2Won;
    }
}
