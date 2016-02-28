package com.geistman.gtournament;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static android.view.View.*;

public class SetWinner extends AppCompatActivity {

    private static final String TAG = "Set Winner";
    private static final String PLAYER1_TAG = "PLAYER1";
    private static final String PLAYER2_TAG = "PLAYER2";
    private static final String WINNER_BUTTON_TAG = "com.geistman.gtournament.WINNER_BUTTON_TAG";


    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_winner);

        final Intent intent = getIntent();
        game = intent.getParcelableExtra(ChoosePlayer.GAME);
        Log.d(TAG, game.toString());

        final Button player1Button = (Button) findViewById(R.id.player1);
        player1Button.setText(game.getPlayer1());
        player1Button.setTag(PLAYER1_TAG);


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


        if (game.getWinner() != null) {
            ViewGroup parentView = (ViewGroup) findViewById(R.id.playerViewGroup);
            String winnerButtonTag = intent.getCharSequenceExtra(WINNER_BUTTON_TAG).toString();
            setEnabledOfAllElementsOfViewExcept(parentView, winnerButtonTag, false);
            Button winnersButton = getWinnersButton(winnerButtonTag);
            winnersButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    GameHistory.addGame(game);
                    Intent newGame = new Intent(getApplicationContext(), ChoosePlayer.class);
                    newGame.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(newGame);
                }
            });
        }

        else {
            player1Button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getConfirmationForWinner(player1Button);
                }
            });
            player2Button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    getConfirmationForWinner(player2Button);

                }
            });
        }
    }

    private Button getWinnersButton(String winnerButtonTag) {
        if (winnerButtonTag.equals(PLAYER1_TAG))
            return (Button) findViewById(R.id.player1);
        else
            return (Button) findViewById(R.id.player2);
    }

    private void getConfirmationForWinner(Button winnerButton) {
        game.setWinner((String) winnerButton.getText());
        Intent confirmWinner = new Intent(this, SetWinner.class);
        confirmWinner.putExtra(ChoosePlayer.GAME, game);
        confirmWinner.putExtra(WINNER_BUTTON_TAG, (CharSequence) winnerButton.getTag());
        startActivity(confirmWinner);
    }

    private void setEnabledOfAllElementsOfViewExcept(ViewGroup thisViewGroup, String exceptThisButtonTag, boolean enOrDisable) {

        for (int i = 0; i < thisViewGroup.getChildCount(); i++){
            View child = thisViewGroup.getChildAt(i);
            Object childTag = child.getTag();
            if (childTag == null || !childTag.equals(exceptThisButtonTag))
                child.setEnabled(enOrDisable);
            else
                child.setEnabled(!enOrDisable);
            if (child instanceof ViewGroup){
                setEnabledOfAllElementsOfViewExcept((ViewGroup) child, exceptThisButtonTag, enOrDisable);
            }
        }
    }

    private void startNewGame() {
        finish();
    }
}
