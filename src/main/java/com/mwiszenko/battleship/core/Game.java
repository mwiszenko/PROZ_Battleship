package com.mwiszenko.battleship.core;

import com.mwiszenko.battleship.gui.BoardPanel;
import com.mwiszenko.battleship.net.NetworkClient;
import com.mwiszenko.battleship.net.NetworkServer;
import com.mwiszenko.battleship.utils.DialogHandler;
import com.mwiszenko.battleship.utils.ImageLoader;
import com.mwiszenko.battleship.utils.SetupProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.AbstractMap;

public class Game extends JFrame {
    private final JFrame parentFrame;
    private final String gameType;
    private final SetupProvider setupProvider;
    private BoardPanel[] panels;
    private AI opponent;
    private NetworkServer server;
    private NetworkClient client;
    private boolean isHost;

    public Game(JFrame parentFrame, String gameType) {
        this.parentFrame = parentFrame;
        this.gameType = gameType;
        switch (gameType) {
            case "host" -> {
                server = new NetworkServer(this, parentFrame);
                isHost = true;
            }
            case "join" -> {
                client = new NetworkClient(this, parentFrame);
                isHost = false;
            }
            case "local" -> {
                opponent = new AI();
                isHost = true;
            }
        }
        this.setupProvider = new SetupProvider();
    }


    public void startGame(Board board1, Board board2) {
        setTitle("Battleship");
        panels = new BoardPanel[2];
        panels[0] = new BoardPanel(ImageLoader.getImage("bg-left.jpg"), board1, false);
        add(panels[0], BorderLayout.WEST);
        panels[1] = new BoardPanel(ImageLoader.getImage("bg-right.jpg"), board2, isHost);
        add(panels[1], BorderLayout.EAST);
        pack();
        initKeyListeners();
        initMouseListener();
        setJMenuBar(initMenuBar());

        setLocationRelativeTo(parentFrame);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        parentFrame.setVisible(false);
        if (server != null || client != null) startConnection();
        else localSetup();
    }

    private void startConnection() {
        boolean goodInput = true;
        int port = 0, timeout = 0;
        switch (gameType) {
            case "host" -> {
                try {
                    while(isInvalidInt(port = Integer.parseInt(getInputFromTextField(
                            "Enter port (range 1024-65535)")), 1024, 65535)) {
                        DialogHandler.showConfirmDialog(this, "Number not in range", "Wrong input");
                    }
                    while(isInvalidInt(timeout = Integer.parseInt(getInputFromTextField(
                            "Enter timeout in seconds (range 0-60)")), 0, 60)) {
                        DialogHandler.showConfirmDialog(this, "Number not in range", "Wrong input");
                    }
                } catch (NumberFormatException e) {
                    DialogHandler.showConfirmDialog(this, "Not a number", "Wrong input");
                    goodInput = false;
                }
                if (goodInput) {
                    server.start(port, timeout * 1000);
                    panels[1].setActivity();
                } else closeGame();
            }
            case "join" -> {
                String serverName = getInputFromTextField("Enter server name");
                try {
                    while(isInvalidInt(port = Integer.parseInt(getInputFromTextField(
                            "Enter port (range 1024-65535)")), 1024, 65535)) {
                        DialogHandler.showConfirmDialog(this, "Number not in range", "Wrong input");
                    }
                } catch (NumberFormatException e) {
                    DialogHandler.showConfirmDialog(this, "Not a number", "Wrong input");
                    goodInput = false;
                }
                if (goodInput) {
                    client.connect(serverName, port);
                } else closeGame();
            }
        }
    }

    public void closeGame() {
        if (server != null) server.stop();
        if (client != null) client.stop();
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
                if(e.getKeyCode() == KeyEvent.VK_X) {

                    closeGame();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }


    private boolean checkEnd() {
        boolean end1, end2;
        end1 = panels[0].checkIfAllSunk();
        end2 = panels[1].checkIfAllSunk();
        if (end1) {
            DialogHandler.showConfirmDialog(this, "You lost. Good luck next time!", "Loss");
            closeGame();
        } else if (end2) {
            DialogHandler.showConfirmDialog(this, "Congratulations! You won!", "Win");
            closeGame();
        }
        return end1 || end2;
    }


    private void initMouseListener() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int z = e.getButton();
                int panelNumber = x / 400;
                int column = (x - Board.BOARD_OFFSET - panelNumber * 400) / Tile.TILE_WIDTH;
                int row = (y - Board.BOARD_OFFSET - 50) / Tile.TILE_HEIGHT;
                if (panelNumber == 1 && row >= 0 && row <= 9 && column >= 0 && column <= 9) {
                    if (z == 1 && panels[1].isActive() && panels[1].isValidMove(row, column)) {
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
        if (server != null && server.isConnected()) {
            panels[1].makeMove(row, column);
            server.send("M" + row + column);
            panels[1].setActivity();
            repaint();
            checkEnd();
        } else if (client != null && client.isConnected()) {
            panels[1].makeMove(row, column);
            client.send("M" + row + column);
            panels[1].setActivity();
            repaint();
            checkEnd();
        } else if (opponent != null) {
            panels[1].makeMove(row, column);
            panels[1].setActivity();
            repaint();
            if (!checkEnd()) {
                makeAIMove();
                panels[1].setActivity();
                repaint();
            }
        }
    }

    private void makeOpponentMove(int row, int column) {
        panels[0].makeMove(row, column);
        panels[1].setActivity();
        repaint();
        checkEnd();
    }

    private void makeAIMove() {
        AbstractMap.SimpleEntry<Integer, Integer> move = opponent.getNextMove();
        panels[0].makeMove(move.getKey(), move.getValue());
        repaint();
        checkEnd();
    }

    private void flagField(int row, int column) {
        panels[1].flagField(row, column);
        repaint();
    }

    private JMenuBar initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(initMenu());
        return menuBar;
    }

    private JMenu initMenu() {
        JMenu menu = new JMenu("Menu");

        JMenuItem exit = new JMenuItem("Exit (X)");
        exit.addActionListener(e -> closeGame());
        menu.add(exit);

        return menu;
    }

    public void receiveMessage(String data) {
        switch (data.charAt(0)) {
            case '0' -> {
                closeGame();
                DialogHandler.showConfirmDialog(parentFrame, "Lost connection to opponent", "Connection error");
            }
            case 'M' -> {
                int row = data.charAt(1) - '0';
                int column = data.charAt(2) - '0';
                makeOpponentMove(row, column);
            }
            case 'S' -> {
                int number = data.charAt(1) - '0';
                int length = data.charAt(2) - '0';
                int column = data.charAt(3) - '0';
                int row = data.charAt(4) - '0';
                char direction = data.charAt(5);

                panels[1].addShip(number, length, column, row, direction);
            }
        }
    }



    public void onlineSetup() {
        int column, row, length, number;
        char direction;
        Ship[] setup = setupProvider.getSetup();
        for (Ship ship : setup) {
            column = ship.getXPos();
            row = ship.getYPos();
            length = ship.getLength();
            number = ship.getNumber();
            direction = ship.getDirection();
            panels[0].addShip(number, length, column, row, direction);

            if (server != null) server.send("S" + number + length + column + row + direction);
            else if (client != null) client.send("S" + number + length + column + row + direction);
        }
        repaint();
        DialogHandler.showConfirmDialog(this, "Successfully connected to opponent", "Connection established");
    }

    private void localSetup() {
        int column, row, length, number;
        char direction;
        Ship[] setup = setupProvider.getSetup();
        for (Ship ship : setup) {
            column = ship.getXPos();
            row = ship.getYPos();
            length = ship.getLength();
            number = ship.getNumber();
            direction = ship.getDirection();
            panels[0].addShip(number, length, column, row, direction);
        }
        setup = setupProvider.getSetup();
        for (Ship ship : setup) {
            column = ship.getXPos();
            row = ship.getYPos();
            length = ship.getLength();
            number = ship.getNumber();
            direction = ship.getDirection();
            panels[1].addShip(number, length, column, row, direction);
        }
        repaint();
    }

    public boolean isInvalidInt(int data, int min, int max) {
        return data < min || data > max;
    }

    public String getInputFromTextField(String message) {
        return (String) JOptionPane.showInputDialog(this, message, "Input", JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(ImageLoader.getImage("icon.png")), null, null);
    }
}
