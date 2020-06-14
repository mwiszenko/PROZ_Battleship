package com.mwiszenko.battleship.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void makeMove() {
        Tile tile = new Tile(0,0);
        assertFalse(tile.isHit());
        assertNull(tile.getImage());
        tile.makeMove();
        assertTrue(tile.isHit());
        assertNotNull(tile.getImage());
    }

    @Test
    void addSegment() {
        Tile tile = new Tile(0,0);
        ShipSegment segment = new ShipSegment(0, 0);
        assertNull(tile.getSegment());
        tile.addSegment(segment);
        assertNotNull(tile.getSegment());
    }

    @Test
    void flagField4Times() {
        Tile tile = new Tile(0, 0);
        assertEquals(0, tile.getFlag());
        assertNull(tile.getImage());
        tile.flagField();
        assertEquals(1, tile.getFlag());
        tile.flagField();
        assertEquals(2, tile.getFlag());
        tile.flagField();
        assertEquals(3, tile.getFlag());
        tile.flagField();
        assertEquals(0, tile.getFlag());
        assertNull(tile.getImage());
    }
}