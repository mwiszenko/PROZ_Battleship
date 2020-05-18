package com.mwiszenko.battleship.gui;

import javax.swing.*;
import java.awt.*;

public class AppPanel extends JPanel
{
    public AppPanel()
    {
        setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        setLayout(new GridLayout(0,1));
    }
}
