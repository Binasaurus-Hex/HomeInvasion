package game;

import objects.handlers.KeyHandler;
import objects.handlers.MouseHandler;

import javax.swing.*;
import java.awt.*;

public class Window {
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public int gameWidth = (int)screenSize.getWidth();
    public int gameHeight = (int)screenSize.getHeight();

    public Window(Component comp, String title,KeyHandler keyHandler, MouseHandler mouseHandler) {
        JFrame frame = new JFrame(title);
        frame.setLocation((int)((screenSize.getWidth()/2)-(gameWidth/2)), (int)((screenSize.getHeight()/2)-(gameHeight/2))-20);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, comp);
        frame.getContentPane().setBackground(Color.black);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setSize(gameWidth, gameHeight);
        frame.setVisible(true);
        frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        frame.addKeyListener(keyHandler);
        frame.addMouseListener(mouseHandler);
        frame.addMouseMotionListener(mouseHandler);
    }
}
