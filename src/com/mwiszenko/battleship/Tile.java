package com.mwiszenko.battleship;

import java.awt.*;
import java.awt.event.ActionListener;

public class Tile
{
    public static final int TILE_WIDTH = 40;
    public static final int TILE_HEIGHT = 40;

    private Image image;
    private int row, column;
    private int xPos,yPos;
    private boolean isHit;

    private ShipSegment segment;

    public Tile(int row, int column, int xPos, int yPos)
    {
        this.row = row;
        this.column = column;
        this.xPos = xPos;
        this.yPos = yPos;
        isHit = false;
        image = null;
    }

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
    public void setRow(int row)
    {
        this.row = row;
    }
    public void setColumn(int column)
    {
        this.column = column;
    }
    public boolean isHit() {return isHit;}
    public Image getImage()
    {
        return image;
    }
    protected void makeMove() {
        this.isHit = true;
        this.image = ImageLoader.getImage("fire.png");
        if(segment != null) this.segment.makeHit();
    }
    public void addSegment(ShipSegment segment) {
        this.segment = segment;
    }
}
