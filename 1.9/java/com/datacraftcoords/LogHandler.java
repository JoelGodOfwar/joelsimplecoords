package com.datacraftcoords;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogHandler {
    private static Logger logger = LogManager.getLogger("JoelGodOfWars.Simple.Coordinate.Mod");

    public static void log(Level logLevel, Object object)
    {
        logger.log(logLevel, String.valueOf(object));
    }

    public static void error(Object object)
    {
        log(Level.ERROR, object);
    }

    public static void debug(Object object)
    {
        log(Level.DEBUG, object);
    }

    public static void warning(Object object)
    {
        log(Level.WARN, object);
    }

    public static void info(Object object)
    {
        log(Level.INFO, object);
    }
	
    public static void infoBox(String infoMessage, String titleBar)
    {
        int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog (null, infoMessage,titleBar,dialogButton);
		if(dialogResult == JOptionPane.YES_OPTION){
			//JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
			openWebpage("http://minecraft.curseforge.com/mc-mods/221140-version-checker/files/2242815/download");
		}
    }
	
	public static void openWebpage(URI uri)
    {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
        {
            try
            {
                desktop.browse(uri);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
	
	public static void openWebpage(URL url)
    {
        try
        {
            openWebpage(url.toURI());
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    public static void openWebpage(String string)
    {
        try
        {
            openWebpage(new URI(string));
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }
}
