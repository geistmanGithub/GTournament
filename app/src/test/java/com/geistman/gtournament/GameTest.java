package com.geistman.gtournament;

import android.os.Parcel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @Test
    public void testAddTooManyPlayers() throws Exception {
        int i;

        for (i = 1; i<3; i++) {
            assertThat("Adding player "+i, game.addPlayer(Integer.toString(i)), is(true));
        }
        assertThat(game.addPlayer(Integer.toString(i)), is(false));
    }

    @Test
    public void testGameParcelingAndDeParceling() throws Exception {
        Parcel parcel = Parcel.obtain();
        game.writeToParcel(parcel, 0);

        parcel.setDataPosition(0);
        Game gameFromParcel = Game.CREATOR.createFromParcel(parcel);

        assertEquals(game, gameFromParcel);
    }
}