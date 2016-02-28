package com.geistman.gtournament;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChoosePlayer extends AppCompatActivity {

    private static final String TAG = "Choose Player";
    private ArrayList<String> playersList;
    public static final String GAME = "com.geistman.gtournament.GAME";


    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);



    }

    @Override
    protected void onStart() {
        super.onStart();

        game = new Game();

        playersList = new ArrayList<>();
        playersList.add("Michael");
        playersList.add("Oliver");
        playersList.add("Herbert");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                playersList);


        final ListView playersListView = (ListView) this.findViewById(R.id.playerList);
        playersListView.setAdapter(arrayAdapter);


        playersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Item selected: " + playersListView.getItemAtPosition(position));

                String player = playersListView.getItemAtPosition(position).toString();
                if (game.addPlayer(player)) {
                    playersList.remove(player);
                    arrayAdapter.notifyDataSetChanged();
                }


                if (game.isGameReady()) {
                    startGame();
                }
            }
        });


    }

    private void startGame() {
        Intent setWinner = new Intent(this, SetWinner.class);
        setWinner.putExtra(GAME, game);
        startActivity(setWinner);
    }

    //TODO: Repopulate list on new game, each time when activity is called again.
}
