package com.mwiszenko.battleship.gui;

import com.mwiszenko.battleship.Game;
import com.mwiszenko.battleship.ImageLoader;
import com.mwiszenko.battleship.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AppFrame extends JFrame
{
    public AppFrame()
    {
        setTitle("Main menu");
        AppPanel panel = new AppPanel(ImageLoader.getImage("bg.jpg"));
        add(panel);
        pack();
        initKeyListeners();
        setJMenuBar(initMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);
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
        newGame.addActionListener(e -> startNewGame());
        menu.add(newGame);

        menu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit (X)");
        newGame.addActionListener(e -> dispose());
        menu.add(exit);

        return menu;
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
                    case KeyEvent.VK_X:
                        dispose();
                        break;
                    case KeyEvent.VK_S:
                        dispose();
                        break;
                    case KeyEvent.VK_L:
                        dispose();
                        break;
                    case KeyEvent.VK_A:
                        dispose();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
        private void startNewGame()
        {
            Game game = new Game(this);
            game.startGame();
        }
}
