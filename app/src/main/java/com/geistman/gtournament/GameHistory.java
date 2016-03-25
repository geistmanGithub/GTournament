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
    private final GameHistoryDbHelper gameHistoryDbHelper;


    public GameHistory(Context context) {
        gameHistoryDbHelper = new GameHistoryDbHelper(context);
        database = gameHistoryDbHelper.getWritableDatabase();
        Log.d(TAG, "Games stored in Database: " + getDatabaseCount());
    }

    public static void addGame(Game game) {
        ContentValues values = new ContentValues();
        values.put(Game.GAME_PLAYER1, game.getPlayer1());
        values.put(Game.GAME_PLAYER2, game.getPlayer2());
        values.put(Game.GAME_WINNER, game.getWinner());

        database.insert(TABLE_NAME, null, values);

        Log.d(TAG, "Number of Games stored in database: " + getDatabaseCount());
    }

    private static int getDatabaseCount() {
        Cursor mCount= database.rawQuery("select count(*) from " + TABLE_NAME, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }


    public int getWinsOfPlayer(String winner, String looser) {
        String[] projection = {
                Game.GAME_WINNER
        };

        String selection = Game.GAME_WINNER+"= '"+winner+"' and ("+Game.GAME_PLAYER1+" = '"+looser+"' or "+Game.GAME_PLAYER2+" = '"+looser+"')";
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

    public String getLastGamesStatForPlayers (String winner, String looser, int numberOfLastGames){
        String query = "Select winner from gameHistory where (player1 = \" "+winner+" \" and player2 = \" "+looser+" \") or (player1 = \" "+looser+" \" and player2 = \" "+winner+" \") order by GameHistory_id desc LIMIT 4";
        Cursor c = database.rawQuery(query, null);

        //TODO: return count for wach winner or subselect with group


        return null;
    }

    public void onCreate(SQLiteDatabase db) {
        gameHistoryDbHelper.onCreate(db);
    }
}
