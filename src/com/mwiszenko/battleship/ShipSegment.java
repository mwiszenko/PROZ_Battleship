package com.mwiszenko.battleship;

import java.awt.*;

public class ShipSegment
{
    private boolean hitStatus;
    private int xPos;
    private int yPos;

    public ShipSegment(int xPos, int yPos)
    {
        this.hitStatus = false;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public boolean getHitStatus()
    {
        return hitStatus;
    }

    public void makeHit() {
        hitStatus = true;
    }

    public int getXPos() {return xPos;}

    public int getYPos() {return yPos;}
}