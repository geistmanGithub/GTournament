package com.geistman.gtournament;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Objects;

import static android.view.View.*;

public class SetWinner extends AppCompatActivity {

    private static final String TAG = "Set Winner";
    private static final String PLAYER1_TAG = "PLAYER1";
    private static final String PLAYER2_TAG = "PLAYER2";


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
        player1Button.setTag(PLAYER1_TAG);
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
        player2Button.setTag(PLAYER2_TAG);
        player2Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getConfirmationForWinner(player2Button);

            }
        });
    }

    private void getConfirmationForWinner(Button winnerButton) {
        game.setWinner((String) winnerButton.getText());
        ViewGroup parentView = (ViewGroup) findViewById(R.id.playerViewGroup);
        setEnabledOfAllElementsOfViewExcept(parentView, winnerButton, false);
    }

    private void setEnabledOfAllElementsOfViewExcept(ViewGroup thisViewGroup, Button exceptThisButton, boolean enOrDisable) {
        Object exceptThisButtonTag = exceptThisButton.getTag();

        for (int i = 0; i < thisViewGroup.getChildCount(); i++){
            View child = thisViewGroup.getChildAt(i);
            Object childTag = child.getTag();
            if (childTag == null || !childTag.equals(exceptThisButtonTag))
                child.setEnabled(enOrDisable);
            else
                child.setEnabled(!enOrDisable);
            if (child instanceof ViewGroup){
                setEnabledOfAllElementsOfViewExcept((ViewGroup) child, exceptThisButton, enOrDisable);
            }
        }
    }

    private void startNewGame() {
        finish();
    }
}
