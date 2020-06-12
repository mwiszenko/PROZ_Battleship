package com.mwiszenko.battleship.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkServer
{

    volatile Socket socket;
    volatile ServerSocket server;
    volatile DataInputStream input;
    volatile DataOutputStream output;
    boolean connected;

    public NetworkServer()
    {
        socket = null;
        server = null;
        input = null;
        output = null;
        connected = false;
    }


    private void start( int port )
    {
        try
        {
            server = new ServerSocket( port );
            socket = server.accept();
            connected = true;
            System.out.println("Connected");
            input = new DataInputStream( socket.getInputStream() );
            output = new DataOutputStream( socket.getOutputStream() );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    public void listen()
    {
        while ( connected )
        {
            try
            {
                String data = input.readUTF();
                System.out.println(data);
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            input.close();
            output.close();
            socket.close();
            connected = false;
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}