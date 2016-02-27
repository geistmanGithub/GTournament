package com.geistman.gtournament;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChoosePlayer extends AppCompatActivity {

    private static final String TAG = "Choose Player";
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);

        ArrayList<String> players = new ArrayList<>();
        players.add("Michael");
        players.add("Oliver");
        players.add("Herbert");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                players );

        final ListView playerList = (ListView) this.findViewById(R.id.playerList);
        playerList.setAdapter(arrayAdapter);

        game = new Game();

        playerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Item selected: " + playerList.getItemAtPosition(position));
                game.addPlayer(playerList.getItemAtPosition(position).toString());

                if (game.isGameReady()) {
                    startGame();
                }
            }
        });

    }

    private void startGame() {
        Intent setWinner = new Intent(this, SetWinner.class);
        startActivity(setWinner);
    }
}
