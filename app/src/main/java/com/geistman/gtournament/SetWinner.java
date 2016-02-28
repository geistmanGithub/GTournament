package com.geistman.gtournament;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import static android.view.View.*;

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

        final Button player1Button = (Button) findViewById(R.id.player1);
        player1Button.setText(game.getPlayer1());
        player1Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getConfirmationForWinner(player1Button);
            }
        });

        final Button cancelButton = (Button) findViewById(R.id.cancelGame);
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });

        final Button player2Button = (Button) findViewById(R.id.player2);
        player2Button.setText(game.getPlayer2());
        player2Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getConfirmationForWinner(player2Button);

            }
        });
    }

    private void getConfirmationForWinner(Button button) {
        game.setWinner((String) button.getText());
    }

    private void startNewGame() {
        finish();
    }
}
