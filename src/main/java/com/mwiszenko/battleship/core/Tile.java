package com.mwiszenko.battleship.core;

import com.mwiszenko.battleship.utils.ImageLoader;

import java.awt.*;

public class Tile {
    public static final int TILE_WIDTH = 30;
    public static final int TILE_HEIGHT = 30;
    private final int xPos, yPos;
    private Image image;
    private boolean isHit;
    private int flag;

    private ShipSegment segment;

    public Tile(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        isHit = false;
        image = null;
        flag = 0;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean isHit() {
        return isHit;
    }

    public Image getImage() {
        return image;
    }

    protected void makeMove() {
        this.isHit = true;
        if (segment != null) {
            this.segment.makeHit();
            this.image = ImageLoader.getImage("fire.png");
        } else this.image = ImageLoader.getImage("miss.png");
    }

    public void addSegment(ShipSegment segment) {
        this.segment = segment;
    }

    public void flagField() {
        if (!isHit()) {
            flag = (flag + 1) % 4;
            if (flag == 0) this.image = null;
            if (flag == 1) this.image = ImageLoader.getImage("flag1.png");
            if (flag == 2) this.image = ImageLoader.getImage("flag2.png");
            if (flag == 3) this.image = ImageLoader.getImage("flag3.png");
        }
    }
}
