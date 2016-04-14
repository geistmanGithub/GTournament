package com.geistman.gtournament;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameHistoryDbHelper extends android.database.sqlite.SQLiteOpenHelper{

    private static String TAG = GameHistoryDbHelper.class.getName();
    private static final String DATABASE_NAME ="GameHistoryDB";
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_CREATE_GameHistory =
            "create table "+GameHistory.TABLE_NAME+" (" +
                    GameHistory.TABLE_NAME+Game._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                    Game.COLUMN_Player1 +" text, " +
                    Game.COLUMN_Player2 +" text, " +
                    Game.COLUMN_WINNER +" text" +
            ");";


    private static final String DATABASE_CREATE_Players =
            "create table "+ Player.TABLE_NAME +" ("+
                    Player.TABLE_NAME +Player._ID+" TEXT PRIMARY KEY NOT NULL" +
                    ");"
            ;

    private static final String DATABASE_CREATE_ELO_History =
            "create table ELO_History (" +
                    GameHistory.TABLE_NAME+Game._ID+" INTEGER NOT NULL," +
                    Player.TABLE_NAME+Player._ID+" INTEGER NOT NULL," +
                    Player.COLUMN_TT_ELO +" INTEGER NOT NULL," +
                    "PRIMARY KEY ("+GameHistory.TABLE_NAME+Game._ID+","+Player.TABLE_NAME+Player._ID+"),"+
                    "FOREIGN KEY("+GameHistory.TABLE_NAME+Game._ID+") REFERENCES "+GameHistory.TABLE_NAME+"("+GameHistory.TABLE_NAME+Game._ID+")," +
                    "FOREIGN KEY("+Player.TABLE_NAME+Player._ID+") REFERENCES "+Player.TABLE_NAME+"("+Player.TABLE_NAME+Player._ID+")"+
                    ");"
            ;

    public GameHistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_GameHistory);
        db.execSQL(DATABASE_CREATE_Players);
        db.execSQL(DATABASE_CREATE_ELO_History);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                Log.w(TAG, "Adding Auto Increment ID to Table gameHistory. Games will be preserved");
                String DATABASE_UPGRADE_BackupOldGameHistory = "ALTER TABLE gameHistory RENAME TO v1gameHistory";
                db.execSQL(DATABASE_UPGRADE_BackupOldGameHistory);

                onCreate(db);

                String DATABASE_RESTORE_BackupValuesFromOldGameHistory =
                        "Insert into gameHistory(player1, player2, winner) " +
                                "Select player1, player2, winner " +
                                "from v1gameHistory;";
                db.execSQL(DATABASE_RESTORE_BackupValuesFromOldGameHistory);
                db.execSQL("DROP TABLE v1gameHistory;");

            case 2:
                onCreate(db);
                Log.w(TAG, "Adding new player's table and adding existing players from game's history to it");
                String DATABASE_EXTRACT_EXISTING_PLAYERS =
                        "insert into players (name) select distinct player from (select player1 as player from gameHistory union select player2 as player from gameHistory);";
                //TODO: Populate ELO_History. Default Value is 400 if no value exists. Formula: https://de.wikipedia.org/wiki/Elo-Zahl#Tischtennis
        }
    }
}
