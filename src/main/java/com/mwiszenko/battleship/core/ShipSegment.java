package com.mwiszenko.battleship.core;

public class ShipSegment {
    private boolean hitStatus;
    private final int xPos;
    private final int yPos;

    public ShipSegment(int xPos, int yPos) {
        this.hitStatus = false;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public boolean getHitStatus() {
        return hitStatus;
    }

    public void makeHit() {
        hitStatus = true;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
}