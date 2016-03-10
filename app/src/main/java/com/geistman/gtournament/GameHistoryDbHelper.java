package com.geistman.gtournament;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameHistoryDbHelper extends android.database.sqlite.SQLiteOpenHelper{

    private static String TAG = GameHistoryDbHelper.class.getName();
    private static final String DATABASE_NAME ="GameHistoryDB";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table "+GameHistory.TABLE_NAME+" (" +
            "_"+Game.GAME_TIME+"long primary key, " +
            Game.GAME_PLAYER1+" text, " +
            Game.GAME_PLAYER2+" text, " +
            Game.GAME_WINNER+" text);";

    public GameHistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS Games");
        onCreate(db);
    }
}
