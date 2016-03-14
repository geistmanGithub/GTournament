package com.geistman.gtournament;

import junit.framework.TestCase;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class GameTest extends TestCase {

    private Game game;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        game = new Game();
    }

    @org.junit.Test
    public void testAddTooManyPlayers() throws Exception {
        for (int i = 0; i < 4; i++) {
            game.addPlayer("NameOfPlayer"+i);
        }
        assertThat
    }

    @org.junit.Test
    public void testSetWinner() throws Exception {

    }

    @org.junit.Test
    public void testIsGameReady() throws Exception {

    }

    @org.junit.Test
    public void testGetPlayer1Won() throws Exception {

    }

    @org.junit.Test
    public void testGetPlayer2Won() throws Exception {

    }
}