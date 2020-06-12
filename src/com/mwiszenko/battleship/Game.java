package com.mwiszenko.battleship;

import com.mwiszenko.battleship.gui.BoardPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.AbstractMap;

public class Game extends JFrame
{
    private final JFrame parentFrame;
    private BoardPanel[] panels;
    private AI opponent;

    public Game( JFrame parentFrame )
    {
        this.parentFrame = parentFrame;
        opponent = new AI();
    }

    public void startGame(Board board1, Board board2)
    {
        panels = new BoardPanel[2];
        panels[0] = new BoardPanel(ImageLoader.getImage("bg-left.jpg"), board1, false);
        add(panels[0], BorderLayout.WEST);
        panels[1] = new BoardPanel(ImageLoader.getImage("bg-right.jpg"), board2, false);
        add(panels[1], BorderLayout.EAST);
        pack();
        initKeyListeners();
        initMouseListener();
        setJMenuBar(initMenuBar());

        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        parentFrame.setVisible(false);
    }

    private void closeGame() {
        parentFrame.setVisible(true);
        dispose();
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


    private boolean checkEnd()
    {
        boolean end1, end2;
        end1 = panels[0].checkIfAllSunk();
        end2 = panels[1].checkIfAllSunk();
        if (end1) {
            JOptionPane.showConfirmDialog( this, "You lost", "Loss",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE );
            closeGame();
        }
        else if (end2) {
            JOptionPane.showConfirmDialog( this, "You won", "Win",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE );
            closeGame();
        }
        return end1 || end2;
    }


    private void initMouseListener() {
        addMouseListener( new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int z = e.getButton();
                int panelNumber = x/400;
                int column = (x-50-panelNumber*400)/Tile.TILE_WIDTH;
                int row = (y-96)/Tile.TILE_HEIGHT;
                if(panelNumber == 1 && row >= 0 && row <= 9 && column >= 0 && column <= 9) {
                    if (z == 1 && panels[1].isValidMove(row, column)) {
                        makeMove(row, column);
                    }
                    if (z == 3) {
                        flagField(row, column);
                    }
                }
            }
        });
    }

    private void makeMove(int row, int column) {
        panels[1].makeMove(row, column);
        repaint();
        if(!checkEnd()) makeAIMove(row, column);
    }

    private void makeAIMove(int row, int column) {
        AbstractMap.SimpleEntry<Integer, Integer> move = opponent.getNextMove();
        panels[0].makeMove(move.getKey(), move.getValue());
        repaint();
        checkEnd();
    }

    private void flagField(int row, int column) {
        panels[1].flagField(row, column);
        repaint();
    }

    private JMenuBar initMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(initMenu());
        return menuBar;
    }

    private JMenu initMenu()
    {
        JMenu menu = new JMenu("Game");

        JMenuItem newGame = new JMenuItem("Start new game (S)");
        newGame.addActionListener(e -> dispose());
        menu.add(newGame);

        menu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit (X)");
        exit.addActionListener(e -> dispose());
        menu.add(exit);

        return menu;
    }
}
