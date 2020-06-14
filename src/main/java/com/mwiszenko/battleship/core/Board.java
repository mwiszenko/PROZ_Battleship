package com.mwiszenko.battleship.core;

import com.mwiszenko.battleship.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    public static final int BOARD_OFFSET = 50;

    protected final Tile[][] tiles;
    private final Ship[] ships;
    private final boolean displayShipsFlag;
    private boolean allSunk;
    private int lastMoveXPos;
    private int lastMoveYPos;
    private boolean displayLastMoveFlag;

    public Board(int xOffset, int yOffset, boolean displayShipsFlag) {
        tiles = new Tile[10][10];
        initTiles(xOffset, yOffset);
        ships = new Ship[5];
        allSunk = false;
        displayLastMoveFlag = false;
        this.displayShipsFlag = displayShipsFlag;
    }

    private void initTiles(int xOffset, int yOffset) {
        for (int column = 0; column < 10; column++) {
            for (int row = 0; row < 10; row++) {
                this.tiles[column][row] = new Tile(xOffset + Tile.TILE_WIDTH * column,
                        yOffset + Tile.TILE_HEIGHT * row);
            }
        }
    }

    public void addShip(int number, int length, int column, int row, char vertical) {
        ships[number] = new Ship(number, length, BOARD_OFFSET + column * Tile.TILE_WIDTH,
                BOARD_OFFSET + row * Tile.TILE_HEIGHT, vertical);
        ShipSegment[] segments = ships[number].getSegments();
        for (ShipSegment segment : segments) {
            row = (segment.getYPos() - BOARD_OFFSET) / Tile.TILE_HEIGHT;
            column = (segment.getXPos() - BOARD_OFFSET) / Tile.TILE_WIDTH;
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

    public void drawImages(Graphics graphics, boolean isActive) {
        graphics.drawImage(ImageLoader.getImage("board.jpg"), Board.BOARD_OFFSET,
                Board.BOARD_OFFSET, null);
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                graphics.setColor(Color.BLACK);
                graphics.drawRect(tile.getXPos(), tile.getYPos(), Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            }
        }
        for (Ship ship : ships) {
            if (ship != null && (displayShipsFlag || ship.isSunk())) {
                graphics.drawImage(ship.getImage(), ship.getXPos(), ship.getYPos(), null);
            }
        }
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                graphics.drawImage(tile.getImage(), tile.getXPos() + 5, tile.getYPos() + 5, null);
            }
        }
        if (displayLastMoveFlag) {
            graphics.setColor(Color.GREEN);
            graphics.drawRoundRect(lastMoveXPos * Tile.TILE_WIDTH + Board.BOARD_OFFSET,
                    lastMoveYPos * Tile.TILE_HEIGHT + Board.BOARD_OFFSET,
                    Tile.TILE_WIDTH, Tile.TILE_HEIGHT, 5, 10);
        }
        if(isActive) graphics.setColor(Color.GREEN);
        else graphics.setColor(Color.RED);
        graphics.drawRect(Board.BOARD_OFFSET, Board.BOARD_OFFSET, 10 * Tile.TILE_WIDTH, 10 * Tile.TILE_HEIGHT);
    }

    public void makeMove(int row, int column) {
        displayLastMoveFlag = true;
        lastMoveXPos = column;
        lastMoveYPos = row;
        tiles[column][row].makeMove();
    }

    public boolean isValidMove(int row, int column) {
        if(row < 0 || row > 10 || column < 0 || column > 10) return false;
        return !tiles[column][row].isHit();
    }

    public void flagField(int row, int column) {
        if(row >= 0 && row <= 10 && column >= 0 && column <= 10) tiles[column][row].flagField();
    }
}