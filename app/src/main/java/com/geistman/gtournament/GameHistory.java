package com.geistman.gtournament;

import android.util.ArraySet;
import android.util.Log;

import java.util.ArrayList;

public class GameHistory {

    private static final String TAG = "GAME HISTORY";
    private static ArrayList<Game> gameList = new ArrayList<>();


    public static void addGame(Game game) {
        gameList.add(game);
        Log.d(TAG, "Game added:"+game.toString());
        Log.d(TAG, "Number of Games stored: "+gameList.size());
    }
}
