package com.mwiszenko.battleship.core;

import com.mwiszenko.battleship.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel
{
    public static final int BOARD_OFFSET = 50;

    protected final Tile[][] tiles;
    private final Ship[] ships;
    private boolean allSunk;
    private int lastMoveXPos;
    private int lastMoveYPos;
    private boolean displayLastMoveFlag;
    private final boolean displayShipsFlag;

    public Board(int xOffset, int yOffset, boolean displayShipsFlag)
    {
        tiles = new Tile[10][10];
        initTiles(xOffset, yOffset);
        ships = new Ship[5];
        addShip(0, 2, "2.jpg", 50, 50);
        addShip(1, 3, "3.png", 50, 110);
        addShip(2, 3, "3.png", 50, 140);
        addShip(3, 4, "4.png", 50, 200);
        addShip(4, 5, "5.png", 50, 230);
        allSunk = false;
        displayLastMoveFlag = false;
        this.displayShipsFlag = displayShipsFlag;
    }

    private void initTiles(int xOffset, int yOffset) {
        for( int column = 0; column < 10; column++)
        {
            for(int row = 0; row < 10; row++)
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
            row = (segment.getYPos()-50)/30;
            column = (segment.getXPos()-50)/30;
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
        graphics.drawImage(ImageLoader.getImage("white.jpg"), 50, 50, null);
        for( Tile[] row : tiles)
        {
            for( Tile tile : row)
            {
                graphics.setColor(Color.BLACK);
                graphics.drawRect(tile.getXPos(), tile.getYPos(), Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            }
        }
        for (Ship ship : ships) {
            if(displayShipsFlag) {
                graphics.drawImage(ship.getImage(), ship.getXPos(), ship.getYPos(), null);
            }
        }
        for( Tile[] row : tiles)
        {
            for( Tile tile : row)
            {
                graphics.drawImage(tile.getImage(), tile.getXPos() + 5, tile.getYPos() + 5, null);
            }
        }
        if(displayLastMoveFlag) {
            graphics.setColor(Color.GREEN);
            graphics.drawRoundRect(lastMoveXPos * 30 + 50, lastMoveYPos * 30 + 50, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, 10, 10);
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