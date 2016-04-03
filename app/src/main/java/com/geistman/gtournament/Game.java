package com.geistman.gtournament;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Game implements Parcelable, BaseColumns{

    //When adding an attribute also check on the Parcel Generator and Parcel Loader!
    private static final String TAG = "GAME";

    //Settings
    private final static int MAXPLAYERS = 2;
    private final static int RECENT_GAMES_TO_CONSIDER = 10;

    //DatabaseContract
    public final static String COLUMN_Player1 = "player1";
    public final static String COLUMN_Player2 = "player2";
    public final static String COLUMN_WINNER = "winner";

    //Attributes
    private String winner;
    private ArrayList<String> players = new ArrayList<>();

    //Helping Attributes
    private int player1Won;
    private int player2Won;

    protected Game(Parcel in) {
        players.add(in.readString());
        players.add(in.readString());
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

    public String getPlayer2() {
        return players.get(1);
    }

    public String getPlayer1() {
        return players.get(0);
    }


    public boolean addPlayer(String player) {
        if (players.size() < MAXPLAYERS) {
            players.add(player);

            if(isGameReady()) {
                final int winsOfPlayer1 = GameHistory.getInstance(null).getWinsOfPlayer(getPlayer1(), getPlayer2());
                final int winsOfPlayer2 = GameHistory.getInstance(null).getWinsOfPlayer(getPlayer2(), getPlayer1());
                setWins(winsOfPlayer1, winsOfPlayer2);
            }
            return true;
        }
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
        return players.size() == 2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        String s = "";
        for (String player: players) {
            s += "Player: "+player;
        }
        
        return "Game{" +
                ", player1Won=" + player1Won +
                ", player2Won=" + player2Won +
                ", winner='" + winner + '\'' +
                '}';
    }


    public void confirmWinner() {
        GameHistory.getInstance(null).addGame(this);
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

    public String getLatestGameStats() {
        return GameHistory.getInstance(null).getLastGamesStatForPlayers(getPlayer1(), getPlayer2(), RECENT_GAMES_TO_CONSIDER);
    }
}
