package com.mwiszenko.battleship.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void makeHitsAndCheckIfSunk() {
        Ship ship = new Ship(0,3, 0, 0, 'v');
        assertFalse(ship.isSunk());
        ShipSegment[] segments = ship.getSegments();
        segments[0].makeHit();
        assertFalse(ship.isSunk());
        segments[1].makeHit();
        assertFalse(ship.isSunk());
        segments[2].makeHit();
        assertTrue(ship.isSunk());
    }
}