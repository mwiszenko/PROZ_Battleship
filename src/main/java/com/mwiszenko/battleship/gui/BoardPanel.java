package com.mwiszenko.battleship.gui;

import com.mwiszenko.battleship.core.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel
{
    private final Image bgImage;
    private Board board;
    private boolean isActive;

    public BoardPanel(Image bgImage, Board board, boolean isActive)
    {
        this.bgImage = bgImage;
        this.board = board;
        this.isActive = isActive;

        setPreferredSize(new Dimension(bgImage.getWidth(null),bgImage.getHeight(null)));
        setOpaque(false);
        setLayout(new GridLayout(8,8));
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        graphics.drawImage(bgImage, 0, 0, null);

        board.drawImages(graphics);
    }

    public boolean checkIfAllSunk()
    {
        return board.checkIfAllSunk();
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActivity() {
        isActive = !isActive;
    }

    public void makeMove(int row, int column) {
        board.makeMove(row,column);
    }

    public boolean isValidMove(int row, int column) {
        return board.isValidMove(row,column);
    }

    public void flagField(int row, int column) {
        board.flagField(row, column);
    }
}
