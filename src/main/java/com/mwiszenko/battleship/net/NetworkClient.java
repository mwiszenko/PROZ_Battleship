package com.mwiszenko.battleship.net;

import com.mwiszenko.battleship.core.Game;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkClient {

    volatile Socket socket;
    volatile DataInputStream input;
    volatile DataOutputStream output;
    boolean connected;
    Game game;

    public NetworkClient(Game game) {
        socket = null;
        input = null;
        output = null;
        connected = false;
        this.game = game;
    }

    public void connect(String serverName, int port) {
        try {
            socket = new Socket(serverName, port);
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            connected = true;

            listen();

            game.onlineSetup();
        } catch (IOException e) {
            game.closeGame();
            game.showConfirmDialog("Unable to establish connection", "Connection error");
        }
    }

    public void listen() {
        Runnable listenTask = () ->
        {
            while (isConnected()) {
                try {
                    String data = input.readUTF();
                    game.receiveMessage(data);
                } catch (IOException e) {
                    game.closeGame();
                    game.showConfirmDialog("Lost connection to opponent", "Connection error");
                }
            }
        };

        Thread listenThread = new Thread(listenTask);
        listenThread.start();
    }

    public void send(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            System.out.println("Connection closed");
        }
    }

    public void stop() {
        if (connected) send("0");
        connected = false;
        close(input);
        close(output);
        close(socket);
    }

    public void close(Closeable closeable) {
        try {
            if (closeable != null)
                closeable.close();
        } catch (IOException e) {
            System.out.println("Cannot close network module: " + closeable.toString() + ".");
        }
    }

    public boolean isConnected() {
        return connected;
    }
}