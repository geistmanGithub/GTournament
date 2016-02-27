package com.geistman.gtournament;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SetWinner extends AppCompatActivity {

    private static final String TAG = "Set Winner";
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_winner);

        Intent intent = getIntent();
        game = intent.getParcelableExtra(ChoosePlayer.GAME);
        Log.d(TAG, game.toString());
    }
}
