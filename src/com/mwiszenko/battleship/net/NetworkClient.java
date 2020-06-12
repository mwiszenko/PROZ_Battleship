package com.mwiszenko.battleship.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkClient
{

    volatile Socket socket;
    volatile DataInputStream input;
    volatile DataOutputStream output;
    boolean connected;

    public NetworkClient()
    {
        socket = null;
        input = null;
        output = null;
        connected = false;
    }

    private void connect( String serverName, int port )
    {
        try
        {
            socket = new Socket( serverName, port );
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
