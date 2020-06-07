package com.mwiszenko.battleship;

import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class ImageLoader
{
    public static Image getImage(String imgName) throws RuntimeException
    {
        Image img = null;
        try
        {
            URL imgURL = ImageLoader.class.getResource(imgName);
            if ( imgURL == null )
            {
                throw new RuntimeException( imgName );
            }
            img = new ImageIcon(imgURL).getImage();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return img;
    }
}
