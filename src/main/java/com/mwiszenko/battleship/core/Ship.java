package com.mwiszenko.battleship.core;

import com.mwiszenko.battleship.utils.ImageLoader;

import java.awt.*;

public class Ship
{
    private ShipSegment[] segments;
    private boolean isSunk;

    private Image image;
    private int xPos;
    private int yPos;

    public Ship(int length, String image, int xPos, int yPos) {
        segments = new ShipSegment[length];
        for ( int i = 0; i < length; i++)
        {
            segments[i] = new ShipSegment(xPos + i*30, yPos);
        }
        isSunk = true;
        this.xPos = xPos;
        this.yPos = yPos;
        this.image = ImageLoader.getImage(image);
    }

    public boolean isSunk()
    {
        isSunk = true;
        for( ShipSegment segment: segments)
        {
            if (!segment.getHitStatus()) {
                isSunk = false;
                break;
            }
        }
        return isSunk;
    }

    public int getXPos()
    {
        return xPos;
    }

    public int getYPos()
    {
        return yPos;
    }

    public Image getImage()
    {
        return image;
    }

    public ShipSegment[] getSegments() {return segments;}
}