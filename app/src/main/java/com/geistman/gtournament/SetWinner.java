package com.geistman.gtournament;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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

        Button player1 = (Button) findViewById(R.id.player1);
        player1.setText(game.getPlayer1());

        Button player2 = (Button) findViewById(R.id.player2);
        player2.setText(game.getPlayer2());


    }
}
