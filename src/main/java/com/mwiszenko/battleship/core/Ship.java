package com.mwiszenko.battleship.core;

import com.mwiszenko.battleship.utils.ImageLoader;

import java.awt.*;

public class Ship {
    private final ShipSegment[] segments;
    private final int xPos;
    private final int yPos;
    private final char vertical;
    private final int length;
    private final int number;
    private boolean isSunk;
    private Image image;

    public Ship(int number, int length, int xPos, int yPos, char vertical) {
        segments = new ShipSegment[length];
        this.number = number;
        this.vertical = vertical;
        this.length = length;
        for (int i = 0; i < length; i++) {
            if (vertical == 'h') {
                segments[i] = new ShipSegment(xPos + i * 30, yPos);
                this.image = ImageLoader.getImage(length + "h.png");
            } else if (vertical == 'v') {
                segments[i] = new ShipSegment(xPos, yPos + i * 30);
                this.image = ImageLoader.getImage(length + "v.png");
            }
        }
        isSunk = false;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public boolean isSunk() {
        boolean sunk = true;
        for (ShipSegment segment : segments) {
            if (!segment.getHitStatus()) {
                sunk = false;
                break;
            }
        }
        if (sunk) {
            isSunk = true;
            if (vertical == 'v') image = ImageLoader.getImage(length + "v-sunk.png");
            else if (vertical == 'h') image = ImageLoader.getImage(length + "h-sunk.png");
        }
        return isSunk;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getLength() {
        return length;
    }

    public char getVertical() {
        return vertical;
    }

    public int getNumber() {
        return number;
    }

    public Image getImage() {
        return image;
    }

    public ShipSegment[] getSegments() {
        return segments;
    }
}