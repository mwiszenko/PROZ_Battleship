package com.mwiszenko.battleship;

import com.mwiszenko.battleship.gui.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame
{
    private final JFrame parentFrame;
    private BoardPanel[] panels;

    public Game( JFrame parentFrame )
    {
        this.parentFrame = parentFrame;
    }

    public void startGame(Board board1, Board board2)
    {
        panels = new BoardPanel[2];
        panels[0] = new BoardPanel(ImageLoader.getImage("bg.jpg"), board1, true, true);
        add(panels[0], BorderLayout.WEST);
        panels[1] = new BoardPanel(ImageLoader.getImage("bg.jpg"), board2, false, false);
        add(panels[1], BorderLayout.EAST);
        pack();
        initKeyListeners();
        initMouseListener();

        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        parentFrame.setVisible(false);
    }

    private void closeGame() {
        setVisible(false);
        parentFrame.setVisible(true);
    }

    private void initKeyListeners() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_X -> closeGame();
                    case KeyEvent.VK_C -> checkEnd();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    private void checkEnd()
    {
        boolean end1, end2;
        end1 = panels[0].checkIfAllSunk();
        end2 = panels[1].checkIfAllSunk();
        if (end1 || end2) closeGame();
    }


    private void initMouseListener() {
        addMouseListener( new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int z = e.getButton();
                int panel = x/400;
                int column = (x-10-panel*400)/Tile.TILE_WIDTH;
                int row = (y-36)/Tile.TILE_HEIGHT;
                if(row >= 1 && row <= 8 && column >=1 && column <=8) {
                    if (z == 1 && panels[panel].isActive() && panels[panel].isValidMove(row, column)) {
                        makeMove(panel, row, column);
                    }
                    if (z == 3 && !panels[panel].isPlayers()) {
                        flagField(panel, row, column);
                    }
                }
            }
        });
    }

    private void makeMove(int panel, int row, int column) {
        panels[panel].makeMove(row, column);
        panels[0].setActivity();
        panels[1].setActivity();
        repaint();
        checkEnd();
    }

    private void flagField(int panel, int row, int column) {
        panels[panel].flagField(row, column);
        repaint();
    }
}
