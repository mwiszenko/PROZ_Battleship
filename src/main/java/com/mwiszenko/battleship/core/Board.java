package com.mwiszenko.battleship.core;

import com.mwiszenko.battleship.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    public static final int BOARD_OFFSET_X = 50;
    public static final int BOARD_OFFSET_Y = 90;

    private final Tile[][] tiles;
    private final Ship[] ships;
    private final boolean displayShipsFlag;
    private boolean allSunk;
    private int lastMoveXPos;
    private int lastMoveYPos;
    private boolean displayLastMoveFlag;

    public Board(boolean displayShipsFlag) {
        tiles = new Tile[10][10];
        initTiles();
        ships = new Ship[5];
        allSunk = false;
        displayLastMoveFlag = false;
        this.displayShipsFlag = displayShipsFlag;
    }

    private void initTiles() {
        for (int column = 0; column < 10; column++) {
            for (int row = 0; row < 10; row++) {
                this.tiles[column][row] = new Tile(BOARD_OFFSET_X + Tile.TILE_WIDTH * column,
                        BOARD_OFFSET_Y + Tile.TILE_HEIGHT * row);
            }
        }
    }

    public void addShip(int number, int length, int column, int row, char vertical) {
        ships[number] = new Ship(number, length, BOARD_OFFSET_X + column * Tile.TILE_WIDTH,
                BOARD_OFFSET_Y + row * Tile.TILE_HEIGHT, vertical);
        ShipSegment[] segments = ships[number].getSegments();
        for (ShipSegment segment : segments) {
            row = (segment.getYPos() - BOARD_OFFSET_Y) / Tile.TILE_HEIGHT;
            column = (segment.getXPos() - BOARD_OFFSET_X) / Tile.TILE_WIDTH;
            tiles[column][row].addSegment(segment);
        }
    }

    public boolean checkIfAllSunk() {
        allSunk = true;
        for (Ship ship : ships) {
            if (ship != null && !ship.isSunk()) {
                allSunk = false;
                break;
            }
        }
        return allSunk;
    }

    public void drawImages(Graphics graphics, boolean isActive, boolean isOpponents) {
        // board image
        graphics.drawImage(ImageLoader.getImage("board.jpg"), Board.BOARD_OFFSET_X,
                Board.BOARD_OFFSET_Y, null);
        // grid of tiles
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                graphics.setColor(Color.BLACK);
                graphics.drawRect(tile.getXPos(), tile.getYPos(), Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            }
        }
        // ship images
        for (Ship ship : ships) {
            if (ship != null && (displayShipsFlag || ship.isSunk())) {
                graphics.drawImage(ship.getImage(), ship.getXPos(), ship.getYPos(), null);
            }
        }
        // tile images
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                graphics.drawImage(tile.getImage(), tile.getXPos() + 5, tile.getYPos() + 5, null);
            }
        }
        // flag images
        if (displayLastMoveFlag) {
            graphics.setColor(Color.GREEN);
            graphics.drawRoundRect(lastMoveXPos * Tile.TILE_WIDTH + Board.BOARD_OFFSET_X,
                    lastMoveYPos * Tile.TILE_HEIGHT + Board.BOARD_OFFSET_Y,
                    Tile.TILE_WIDTH, Tile.TILE_HEIGHT, 5, 10);
        }
        // board border - color signifies if panel is active and available to hit
        // opponent's and active
        if (isActive) graphics.setColor(Color.GREEN);
        // opponent's and inactive
        else if(isOpponents) graphics.setColor(Color.RED);
        // player's
        else graphics.setColor(Color.BLACK);
        graphics.drawRect(Board.BOARD_OFFSET_X, Board.BOARD_OFFSET_Y, 10 * Tile.TILE_WIDTH,
                10 * Tile.TILE_HEIGHT);
    }

    public void makeMove(int row, int column) {
        displayLastMoveFlag = true;
        lastMoveXPos = column;
        lastMoveYPos = row;
        tiles[column][row].makeMove();
    }

    public boolean isValidMove(int row, int column) {
        if (row < 0 || row > 10 || column < 0 || column > 10) return false;
        return tiles[column][row].isNotHit();
    }

    public void flagField(int row, int column) {
        if (row >= 0 && row <= 10 && column >= 0 && column <= 10) tiles[column][row].flagField();
    }
}