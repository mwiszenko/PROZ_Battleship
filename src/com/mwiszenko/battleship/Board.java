package com.mwiszenko.battleship;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel
{
    protected final Tile[][] tiles;
    private final Ship[] ships;
    private boolean allSunk;
    private int lastMoveXPos;
    private int lastMoveYPos;
    private boolean displayLastMoveFlag;
    private final boolean displayShipsFlag;

    public Board(int xOffset, int yOffset, boolean displayShipsFlag)
    {
        tiles = new Tile[9][9];
        initTiles(xOffset, yOffset);
        ships = new Ship[2];
        addShip(0, 2, "pic.jpg", 10, 10);
        addShip(1, 1, "pic.jpg", 90, 50);
        allSunk = false;
        displayLastMoveFlag = false;
        this.displayShipsFlag = displayShipsFlag;
    }

    private void initTiles(int xOffset, int yOffset) {
        for( int column = 0; column < 9; column++)
        {
            for(int row = 0; row < 9; row++)
            {
                this.tiles[column][row] = new Tile(row, column, xOffset + Tile.TILE_WIDTH * column,
                        yOffset + Tile.TILE_HEIGHT * row);
            }
        }
    }

    public void addShip(int number, int length, String path, int xPos, int yPos) {
        ships[number] = new Ship(length, path, xPos, yPos);
        ShipSegment[] segments = ships[number].getSegments();
        int row, column;
        for(ShipSegment segment: segments) {
            row = (segment.getYPos()-10)/40;
            column = (segment.getXPos()-10)/40;
            tiles[column][row].addSegment(segment);
        }
    }

    public boolean checkIfAllSunk()
    {
        allSunk = true;
        for( Ship ship: ships)
        {
            if (!ship.isSunk()) {
                allSunk = false;
                break;
            }
        }
        return allSunk;
    }

    public void drawImages(Graphics graphics)
    {
        for (Ship ship : ships) {
            if(displayShipsFlag) {
                graphics.drawImage(ship.getImage(), ship.getXPos(), ship.getYPos(), null);
            }
        }
        for( Tile[] row : tiles)
        {
            for( Tile tile : row)
            {
                graphics.setColor(Color.BLACK);
                graphics.drawRoundRect(tile.getXPos(), tile.getYPos(), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, 10, 10);
                graphics.drawImage(tile.getImage(), tile.getXPos() + 10, tile.getYPos() + 10, null);
            }
        }
        if(displayLastMoveFlag) {
            graphics.setColor(Color.GREEN);
            graphics.drawRoundRect(lastMoveXPos * 40 + 10, lastMoveYPos * 40 + 10, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, 10, 10);
        }
    }

    public void makeMove(int row, int column) {
        displayLastMoveFlag = true;
        lastMoveXPos = column;
        lastMoveYPos = row;
        tiles[column][row].makeMove();
    }

    public boolean isValidMove(int row, int column) {
        return !tiles[column][row].isHit();
    }

    public void flagField(int row, int column) {
        tiles[column][row].flagField();
    }
}