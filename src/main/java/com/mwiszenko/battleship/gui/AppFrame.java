package com.mwiszenko.battleship.gui;

import com.mwiszenko.battleship.core.Board;
import com.mwiszenko.battleship.core.Game;
import com.mwiszenko.battleship.utils.ImageLoader;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AppFrame extends JFrame {
    public AppFrame() {
        setTitle("Battleship");
        AppPanel panel = new AppPanel(ImageLoader.getImage("bg.jpg"));
        add(panel);
        pack();
        initKeyListeners();
        setJMenuBar(initMenuBar());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JMenuBar initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(initMenu());
        return menuBar;
    }

    private JMenu initMenu() {
        JMenu menu = new JMenu("Game");

        JMenuItem startLocalGame = new JMenuItem("Start local game against AI (L)");
        startLocalGame.addActionListener(e -> startLocalGame());
        menu.add(startLocalGame);

        menu.addSeparator();

        JMenuItem hostOnlineGame = new JMenuItem("Host online game (H)");
        hostOnlineGame.addActionListener(e -> hostOnlineGame());
        menu.add(hostOnlineGame);

        menu.addSeparator();

        JMenuItem joinOnlineGame = new JMenuItem("Join online game (J)");
        joinOnlineGame.addActionListener(e -> joinOnlineGame());
        menu.add(joinOnlineGame);

        menu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit (X)");
        exit.addActionListener(e -> System.exit(0));
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
                    case KeyEvent.VK_X -> System.exit(0);
                    case KeyEvent.VK_H -> hostOnlineGame();
                    case KeyEvent.VK_J -> joinOnlineGame();
                    case KeyEvent.VK_L -> startLocalGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void startLocalGame() {
        Game game = new Game(this, "local");
        Board playerBoard = new Board(Board.BOARD_OFFSET, Board.BOARD_OFFSET, true);
        Board opponentBoard = new Board(Board.BOARD_OFFSET, Board.BOARD_OFFSET, false);
        game.startGame(playerBoard, opponentBoard);
    }

    private void hostOnlineGame() {
        Game game = new Game(this, "host");
        Board playerBoard = new Board(Board.BOARD_OFFSET, Board.BOARD_OFFSET, true);
        Board opponentBoard = new Board(Board.BOARD_OFFSET, Board.BOARD_OFFSET, false);
        game.startGame(playerBoard, opponentBoard);
    }

    private void joinOnlineGame() {
        Game game = new Game(this, "join");
        Board playerBoard = new Board(Board.BOARD_OFFSET, Board.BOARD_OFFSET, true);
        Board opponentBoard = new Board(Board.BOARD_OFFSET, Board.BOARD_OFFSET, false);
        game.startGame(playerBoard, opponentBoard);
    }

}
