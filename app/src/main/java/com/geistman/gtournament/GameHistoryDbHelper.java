package com.geistman.gtournament;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameHistoryDbHelper extends android.database.sqlite.SQLiteOpenHelper{

    private static String TAG = GameHistoryDbHelper.class.getName();
    private static final String DATABASE_NAME ="GameHistoryDB";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_CREATE = "create table "+GameHistory.TABLE_NAME+" (" +
            GameHistory.TABLE_NAME+Game._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            Game.GAME_PLAYER1+" text, " +
            Game.GAME_PLAYER2+" text, " +
            Game.COLUMN_WINNER +" text);";

    public GameHistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                Log.w(TAG, "Adding Auto Increment ID to Table gameHistory");
                String DATABASE_UPGRADE_BackupOldGameHistory = "ALTER TABLE gameHistory RENAME TO v1gameHistory";
                this.onCreate(db);
                db.execSQL(DATABASE_UPGRADE_BackupOldGameHistory);
                //TODO: Migrate old values to new database.
                //TODO: Drop oldTable

        }
    }
}
