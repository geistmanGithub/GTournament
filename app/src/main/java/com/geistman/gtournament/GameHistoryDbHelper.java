package com.geistman.gtournament;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameHistoryDbHelper extends android.database.sqlite.SQLiteOpenHelper{

    private static String TAG = GameHistoryDbHelper.class.getName();
    private static final String DATABASE_NAME ="GameHistoryDB";
    private static final int DATABASE_VERSION = 1;

    public GameHistoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        final String DATABASE_CREATE_v2 = "create table "+GameHistory.TABLE_NAME+" (" +
                GameHistory.TABLE_NAME+Game._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                Game.COLUMN_Player1 +" text, " +
                Game.COLUMN_Player2 +" text, " +
                Game.COLUMN_WINNER +" text);";

        final String DATABASE_CREATE_v1 = "create table "+GameHistory.TABLE_NAME+" (" +
                Game.COLUMN_Player1+" text, " +
                Game.COLUMN_Player2+" text, " +
                Game.COLUMN_WINNER+" text);";

        String DATABASE_CREATE;

        switch (DATABASE_VERSION) {
            case 1: DATABASE_CREATE = DATABASE_CREATE_v1;
                break;
            case 2: DATABASE_CREATE = DATABASE_CREATE_v2;
                break;
            default: DATABASE_CREATE = DATABASE_CREATE_v2;
        }

        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                Log.w(TAG, "Adding Auto Increment ID to Table gameHistory. Games will be preserved");
                String DATABASE_UPGRADE_BackupOldGameHistory = "ALTER TABLE gameHistory RENAME TO v1gameHistory";
                this.onCreate(db);
                db.execSQL(DATABASE_UPGRADE_BackupOldGameHistory);
                Cursor c = db.rawQuery("Select * from v1gameHistory", null);

                while (c.moveToNext()) {
                    Game game = new Game();
                    game.addPlayer(c.getString(c.getColumnIndex(Game.COLUMN_Player1)));
                    game.addPlayer(c.getString(c.getColumnIndex(Game.COLUMN_Player2)));
                    game.setWinner(c.getString(c.getColumnIndex(Game.COLUMN_WINNER)));
                    game.confirmWinner();
                }


                //TODO: Drop oldTable

        }
    }
}
