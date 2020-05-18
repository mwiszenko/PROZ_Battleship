package com.mwiszenko.battleship;

public class Tile
{
    public static final int TILE_WIDTH = 40;
    public static final int TILE_HEIGHT = 40;

    private int row, column;
    private int xPos,yPos;

    public int getXPos()
    {
        return xPos;
    }
    public void setXPos(int xPos)
    {
        this.xPos = xPos;
    }
    public int getYPos()
    {
        return yPos;
    }
    public void setYPos(int yPos)
    {
        this.yPos = yPos;
    }

}
