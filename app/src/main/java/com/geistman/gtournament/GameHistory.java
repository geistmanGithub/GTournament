package com.geistman.gtournament;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class GameHistory{

    private static final String TAG = "GAME HISTORY";

    private GameHistoryDbHelper dbHelper;
    private static SQLiteDatabase database;

    public static final String TABLE_NAME = "gameHistory";

    private static ArrayList<Game> gameList = new ArrayList<>();

    public GameHistory(Context context) {
        dbHelper = new GameHistoryDbHelper(context);
        database = dbHelper.getWritableDatabase();
        Log.d(TAG, "Games stored in Database: "+getDatabaseCount());
    }

    public static void addGame(Game game) {
        gameList.add(game);
        Log.d(TAG, "Game added:" + game.toString());
        Log.d(TAG, "Number of Games stored temporarily: " + gameList.size());

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

        String selection = Game.GAME_WINNER+"= "+winner+" and ("+Game.GAME_PLAYER1+" = "+looser+" or "+Game.GAME_PLAYER2+" = "+looser+")";


        Cursor c = database.query(
                TABLE_NAME,                               // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // The sort order
        );

        return c.getCount();
    }
}
