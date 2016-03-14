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
    private GameHistory gameHistory;


    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);
        gameHistory = new GameHistory(getApplicationContext());
    }

    @Override
    protected void onStart() {
        super.onStart();

        game = new Game();

        playersList = new ArrayList<>();
        playersList.add("Michael");
        playersList.add("Oliver");
        playersList.add("Herbert");
        playersList.add("Thomas");
        playersList.add("Philipp");

        final ListView playersListView = (ListView) this.findViewById(R.id.playerList);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                playersList);
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
        game.setWins(gameHistory.getWinsOfPlayer(game.getPlayer1(), game.getPlayer2()), gameHistory.getWinsOfPlayer(game.getPlayer2(), game.getPlayer1()));

        Intent setWinner = new Intent(this, SetWinner.class);

        setWinner.putExtra(GAME, game);
        Log.d(TAG, "Game added to intent: "+game.toString());

        startActivity(setWinner);
    }
}
