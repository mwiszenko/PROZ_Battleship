package com.mwiszenko.battleship.gui;

import com.mwiszenko.battleship.Tile;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel
{
    private Image bgImage;
    private Tile[][] tiles;
    public BoardPanel(Image bgImage, int xOffset, int yOffset)
    {
        this.bgImage = bgImage;
        tiles = new Tile[9][9];
        initTiles(tiles, xOffset, yOffset);

        setPreferredSize(new Dimension(400,400));
        setOpaque(false);
        setLayout(new GridLayout(8,8));
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        graphics.drawImage(bgImage, 0, 0, null);

        for( Tile[] row : tiles)
        {
            for (Tile tile : row)
            {
                graphics.setColor(Color.BLACK);
                graphics.drawRect(tile.getXPos(), tile.getYPos(), Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
            }
        }
    }

    private void initTiles(Tile[][] tiles, int xOffset, int yOffset) {
        for( int column =0; column<9; column++)
        {
            for(int row =0; row<9; row++)
            {
                int x = 8 - column;
                int y = 8 - row;
                tiles[column][row] = new Tile();
                tiles[column][row].setXPos(xOffset + Tile.TILE_WIDTH * x);
                tiles[column][row].setYPos(yOffset + Tile.TILE_HEIGHT * y);
            }
        }
    }
}
