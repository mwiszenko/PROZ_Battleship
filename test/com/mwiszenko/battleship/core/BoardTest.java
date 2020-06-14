package com.mwiszenko.battleship.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void checkIfMoveIsValid() {
        Board board = new Board(0, 0, false);
        assertFalse(board.isValidMove(-1, 0));
        assertTrue(board.isValidMove(0, 0));
        board.makeMove(0, 0);
        assertFalse(board.isValidMove(0, 0));
    }
}