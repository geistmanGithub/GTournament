package com.geistman.gtournament;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Arrays;

public class GameHistory {

    private static final String TAG = "GAME HISTORY";

    private static SQLiteDatabase database;
    public static final String TABLE_NAME = "gameHistory";
    private static GameHistory instance;
    private final GameHistoryDbHelper gameHistoryDbHelper;

    private Context context;

    public Context getContext() {
        return context;
    }

    private GameHistory(Context context) {
        gameHistoryDbHelper = new GameHistoryDbHelper(context);
        database = gameHistoryDbHelper.getWritableDatabase();
        Log.d(TAG, "Games stored in Database: " + getDatabaseCount());
        this.context = context;
    }

    public static GameHistory getInstance(Context context)  {
        if (instance == null) {
            instance = new GameHistory(context);
        }
        return instance;
    }

    public void addGame(Game game) {
        ContentValues values = new ContentValues();
        values.put(Game.COLUMN_Player1, game.getPlayer1());
        values.put(Game.COLUMN_Player2, game.getPlayer2());
        values.put(Game.COLUMN_WINNER, game.getWinner());

        database.insert(TABLE_NAME, null, values);

        Log.d(TAG, "Number of Games stored in database: " + getDatabaseCount());
    }

    private int getDatabaseCount() {
        Cursor mCount= database.rawQuery("select count(*) from " + TABLE_NAME, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }


    public int getWinsOfPlayer(String winner, String looser) {
        String[] projection = {
                Game.COLUMN_WINNER
        };

        String selection = Game.COLUMN_WINNER +"= '"+winner+"' and ("+Game.COLUMN_Player1 +" = '"+looser+"' or "+Game.COLUMN_Player2 +" = '"+looser+"')";
        Log.d(TAG, "Querying database: Projection" + Arrays.toString(projection) + " ,Selection: " + selection);

        Cursor c = database.query(
                TABLE_NAME,                               // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                                      // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );
        int count =  c.getCount();
        c.close();
        return count;

    }

    public String getLastGamesStatForPlayers (String player1, String player2, int numberOfLastGames){
        String query = "Select winner from gameHistory where (player1 = \""+player1+"\" and player2 = \""+player2+"\") or (player1 = \""+player2+"\" and player2 = \""+player1+"\") order by GameHistory_id desc LIMIT "+numberOfLastGames;
        Log.w(TAG, "Querying "+database.getPath()+": "+query);
        Cursor c = database.rawQuery(query, null);

        int winsOfPlayer1=0;
        int winsOfPlayer2=0;


        while (c.moveToNext()){
            String winner = c.getString(
                    c.getColumnIndex(Game.COLUMN_WINNER)
            );

            if (winner.equals(player1))
                winsOfPlayer1++;
            else
                winsOfPlayer2++;
        }

        c.close();

        return winsOfPlayer1 + ":" + winsOfPlayer2;
    }

    public void onCreate(SQLiteDatabase db) {
        gameHistoryDbHelper.onCreate(db);
    }
}
