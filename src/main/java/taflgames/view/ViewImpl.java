package taflgames.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import taflgames.controller.Controller;
import taflgames.controller.ControllerImpl;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.Scene;

/**
 * This class implements the view of the application.
 */
public final class ViewImpl implements View {

    private static final String FRAME_TITLE = "Tafl Games";

    private final Controller controller;

    private final JFrame frame;
    private final CardLayout frameLayout;
    private final Set<String> addedScenes;

    /**
     * Sets up the view.
     */
    public ViewImpl() {

        this.controller = new ControllerImpl(this);

        frame = new JFrame(FRAME_TITLE);

        // Set the frame size
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int screenWidth = (int) screen.getWidth();
        final int screenHeight = (int) screen.getHeight();
        frame.setSize((screenWidth * 2) / 3, (screenHeight * 2) / 3);

        // Set frame layout as CardLayout to implement switching between different scenes
        frameLayout = new CardLayout();
        frame.setLayout(frameLayout);

        addedScenes = new HashSet<>();
        setScene(new HomeScene(this));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);  // Let the OS decide about the positioning of the frame
        frame.setVisible(true);
    }

    @Override
    public void setScene(final Scene scene) {
        final String sceneName = scene.getSceneName();
        if (!addedScenes.contains(sceneName)) {
            frame.getContentPane().add(scene.getScene(), sceneName);
            addedScenes.add(sceneName);
        }
        frameLayout.show(frame.getContentPane(), sceneName);
    }

    @Override
    public void close() {
        System.exit(0);
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

}
