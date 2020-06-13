package com.mwiszenko.battleship.core;

import com.mwiszenko.battleship.gui.BoardPanel;
import com.mwiszenko.battleship.net.NetworkServer;
import com.mwiszenko.battleship.net.NetworkClient;
import com.mwiszenko.battleship.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.AbstractMap;

public class Game extends JFrame
{
    private final JFrame parentFrame;
    private BoardPanel[] panels;
    private AI AIopponent;
    private NetworkServer server;
    private NetworkClient client;
    private boolean isHost;
    private final String gameType;

    public Game( JFrame parentFrame, String gameType )
    {
        this.parentFrame = parentFrame;
        this.gameType = gameType;
        switch (gameType) {
            case "host" -> {
                server = new NetworkServer(this);
                isHost = true;
            }
            case "join" -> {
                client = new NetworkClient(this);
                isHost = false;
            }
            case "local" -> {
                AIopponent = new AI();
                isHost = true;
            }
        }
    }


    public void startGame(Board board1, Board board2)
    {
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
        start();
    }

    private void start() {
        boolean goodInput = true;
        int port = 0, timeout = 0;
        switch (gameType) {
            case "host" -> {
                try {
                    port = Integer.parseInt(getInputFromTextField("Enter port"));
                    timeout = Integer.parseInt(getInputFromTextField("Specify timeout (in seconds)"));
                } catch (NumberFormatException e) {
                    showConfirmDialog("Not a number", "Wrong input");
                    goodInput = false;
                }
                if(goodInput) server.start(port, timeout * 1000);
                else closeGame();
            }
            case "join" -> {
                String serverName = getInputFromTextField("Enter server name");
                try{
                    port = Integer.parseInt(getInputFromTextField("Enter port"));
                } catch (NumberFormatException e) {
                    showConfirmDialog("Not a number", "Wrong input");
                    goodInput = false;
                }
                if(goodInput) client.connect(serverName, port);
                else closeGame();
            }
        }
    }

    public void closeGame() {
        if( server != null ) server.stop();
        if( client != null ) client.stop();
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
            showConfirmDialog("You lost", "Loss");
            closeGame();
        } else if (end2) {
            showConfirmDialog("You won", "Win");
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
        if(server != null && server.isConnected()) {
            panels[1].makeMove(row, column);
            server.send("M"+row+column);
            panels[1].setActivity();
            checkEnd();
        }
        else if(client != null && client.isConnected()) {
            panels[1].makeMove(row, column);
            client.send("M"+row+column);
            panels[1].setActivity();
            checkEnd();
        }
        else if(AIopponent != null) {
            panels[1].makeMove(row, column);
            panels[1].setActivity();
            if(!checkEnd()) {
                makeAIMove();
                panels[1].setActivity();
            };
        }
        repaint();
    }

    private void makeOpponentMove(int row, int column) {
        panels[0].makeMove(row, column);
        panels[1].setActivity();
        checkEnd();
        repaint();
    }

    private void makeAIMove() {
        AbstractMap.SimpleEntry<Integer, Integer> move = AIopponent.getNextMove();
        panels[0].makeMove(move.getKey(), move.getValue());
        checkEnd();
        repaint();
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

        JMenuItem exit = new JMenuItem("Exit (X)");
        exit.addActionListener(e -> closeGame());
        menu.add(exit);

        return menu;
    }

    public void receiveMessage(String data) {
        switch(data.charAt(0)) {
            case '0' -> closeGame();
            case 'M' -> {
                int row = data.charAt(1) - '0';
                int column = data.charAt(2) - '0';
                makeOpponentMove(row, column);
            }
            case 'S' -> {
                closeGame();
            }
        }
    }

    public String getInputFromTextField(String message) {
        return JOptionPane.showInputDialog(this, message);
    }

    public void showConfirmDialog(String message, String title) {
        JOptionPane.showConfirmDialog( this, message, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE );
    }
}
