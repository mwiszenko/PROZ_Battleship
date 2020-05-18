package com.mwiszenko.battleship;

import com.mwiszenko.battleship.gui.BoardPanel;

import javax.swing.*;
import java.awt.*;

public class Game extends JFrame
{
    private JFrame parentFrame;
    public Game( JFrame parentFrame )
    {
        this.parentFrame = parentFrame;
    }

    public void startGame()
    {
        BoardPanel panel = new BoardPanel(ImageLoader.getImage("bg.jpg"), 10, 10);
        add(panel, BorderLayout.WEST);
        BoardPanel panel2 = new BoardPanel(ImageLoader.getImage("bg.jpg"), 10, 10);
        add(panel2, BorderLayout.EAST);
        pack();

        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        parentFrame.setVisible(false);
    }
}
