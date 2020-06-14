package com.mwiszenko.battleship.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipSegmentTest {

    @Test
    void makeHitsAndCheckIfSunk() {
        ShipSegment segment = new ShipSegment(0, 0);
        assertFalse(segment.isHit());
        segment.makeHit();
        assertTrue(segment.isHit());
    }
}