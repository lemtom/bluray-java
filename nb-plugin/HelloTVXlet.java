package hellotvxlet;

import java.awt.BorderLayout;
import java.awt.Font;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;

import java.awt.*;
import javax.media.Player;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import org.bluray.net.BDLocator;
import org.davic.media.MediaLocator;

/**
 * Just a simple xlet that draws a String in the center of the screen.
 */
public class HelloTVXlet implements Xlet, Runnable {

    private static Font font;
    private HScene scene;
    private Container gui;
    private static final String message = "Hello World!";
    private Player player = null;

    /**
     * Creates a new instance of HelloTVXlet
     */
    public HelloTVXlet() {
    }

    public void initXlet(XletContext context) {

        font = new Font(null, Font.PLAIN, 50);

        scene = HSceneFactory.getInstance().getDefaultHScene();
        gui = new Container() {

            public void paint(Graphics graphics) {
                graphics.setFont(font);
                graphics.setColor(new Color(120, 120, 10));
                graphics.fillRect(150, 150, getWidth() - 300, getHeight() - 300);
                graphics.setColor(new Color(245, 245, 245));
                int message_width = graphics.getFontMetrics().stringWidth(message);
                graphics.drawString(message, (getWidth() - message_width) / 2, 500);
            }

        };
        scene.repaint();

        gui.setSize(1920, 1080);  // BD screen size
        scene.add(gui, BorderLayout.CENTER);

        scene.validate();
    }

    public void startXlet() {
        gui.setVisible(true);
        scene.setVisible(true);
        new Thread(this).start(); //Starts the code in run()

    }

    public void pauseXlet() {
        gui.setVisible(false);
    }

    public void destroyXlet(boolean unconditional) {
        scene.remove(gui);
        scene = null;
    }

    public void run() {
        try {
            //This is important! Currently, VLC will not run your project without a video file playing
            player = javax.media.Manager.createPlayer(new MediaLocator(new BDLocator(null, -1, 0)));
        } catch (Exception e) {
            //Error
        }
        player.realize();
        player.start();
    }
}
