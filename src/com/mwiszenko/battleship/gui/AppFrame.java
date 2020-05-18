package com.mwiszenko.battleship.gui;

import javax.swing.*;
import java.awt.event.*;

public class AppFrame extends JFrame
{
    public AppFrame()
    {
        setTitle("Main menu");
        setSize(400,400);
        initKeyListeners();
        setJMenuBar(initMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        newGame.addActionListener(e -> dispose());
        menu.add(newGame);

        menu.addSeparator();

        JMenuItem textLogs = new JMenuItem("Show text logs (L)");
        newGame.addActionListener(e -> dispose());
        menu.add(textLogs);

        menu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit (X)");
        newGame.addActionListener(e -> dispose());
        menu.add(exit);

        return menu;
    }

    private void initKeyListeners()
    {
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch(keyCode)
                {
                    case KeyEvent.VK_X:
                        dispose();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
