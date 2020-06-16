package com.mwiszenko.battleship.gui;

import com.mwiszenko.battleship.core.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {
    private final Image bgImage;
    private final Board board;
    // determines if player can make a move on this panel
    private boolean isActive;
    // determines if panel belongs to player or opponent
    private final boolean isOpponents;

    public BoardPanel(Image bgImage, Board board, boolean isOpponents) {
        this.bgImage = bgImage;
        this.board = board;
        this.isActive = false;
        this.isOpponents = isOpponents;

        setPreferredSize(new Dimension(bgImage.getWidth(null), bgImage.getHeight(null)));
        setOpaque(false);
        setLayout(new GridLayout(8, 8));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(bgImage, 0, 0, null);

        board.drawImages(graphics, isActive, isOpponents);
    }

    public boolean checkIfAllSunk() {
        return board.checkIfAllSunk();
    }

    public boolean isActive() {
        return isActive;
    }

    public void changeActivity() {
        isActive = !isActive;
    }

    public void makeMove(int row, int column) {
        board.makeMove(row, column);
    }

    public boolean isValidMove(int row, int column) {
        return board.isValidMove(row, column);
    }

    public void flagField(int row, int column) {
        board.flagField(row, column);
    }

    public void addShip(int number, int length, int column, int row, char vertical) {
        board.addShip(number, length, column, row, vertical);
    }
}
