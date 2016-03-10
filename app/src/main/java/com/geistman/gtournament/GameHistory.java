package com.geistman.gtournament;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.ArraySet;
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
    }

    public static void addGame(Game game) {
        gameList.add(game);

        ContentValues values = new ContentValues();
        values.put(Game.GAME_TIME, game.getGameTime());
        values.put(Game.GAME_PLAYER1, game.getPlayer1());
        values.put(Game.GAME_PLAYER2, game.getPlayer2());
        values.put(Game.GAME_WINNER, game.getWinner());

        database.insert(TABLE_NAME, null, values);

        Log.d(TAG, "Game added:" + game.toString());

        Log.d(TAG, "Number of Games stored: " + gameList.size());
        //TODO: get number of stored entries in db database.rawQuery("count * from " + TABLE_NAME);

    }


}
