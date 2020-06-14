package com.mwiszenko.battleship.net;

import com.mwiszenko.battleship.core.Game;

import javax.swing.*;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer {

    volatile Socket socket;
    volatile ServerSocket server;
    volatile DataInputStream input;
    volatile DataOutputStream output;
    boolean connected;
    Game game;

    public NetworkServer(Game game) {
        socket = null;
        server = null;
        input = null;
        output = null;
        connected = false;
        this.game = game;
    }


    public void start(int port, int timeout) {
        initServer(port, timeout);
        if (server != null)
            connect();
    }

    private void initServer(int port, int timeout) {
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(timeout);
        } catch (IOException e) {
            System.out.println("Unable to create a server socket for port " + port + ".");
        }
    }

    public void connect() {
        Runnable serverTask = () ->
        {
            try {
                socket = server.accept();
                connected = true;
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());

                listen();

                game.onlineSetup();
            } catch (IOException e) {
                game.closeGame();
                game.showConfirmDialog("Unable to establish connection within set timeout",
                        "Connection error");
            }
        };

        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    public void listen() {
        Runnable listenTask = () ->
        {
            while (connected) {
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
        close(server);
        close(input);
        close(output);
        close(socket);
    }

    void close(Closeable closeable) {
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