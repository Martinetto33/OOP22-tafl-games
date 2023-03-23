package taflgames.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import taflgames.controller.Controller;
import taflgames.controller.ControllerImpl;
import taflgames.view.scenecontrollers.HomeControllerImpl;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.Scene;

/**
 * This class implements the view of the application.
 */
public final class ViewImpl implements View {

    private static final String FRAME_TITLE = "Tafl Games";
    private static final double DEFAULT_FRAME_WIDTH_PROP = 0.67;
    private static final double DEFAULT_FRAME_HEIGHT_PROP = 0.67;
    private static final double MIN_FRAME_WIDTH_PROP = 0.50;
    private static final double MIN_FRAME_HEIGHT_PROP = 0.50;

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
        frame.setSize(
            (int) (screenWidth * DEFAULT_FRAME_WIDTH_PROP), 
            (int) (screenHeight * DEFAULT_FRAME_HEIGHT_PROP)
        );
        frame.setMinimumSize(new Dimension(
            (int) (screenWidth * MIN_FRAME_WIDTH_PROP),
            (int) (screenHeight * MIN_FRAME_HEIGHT_PROP)
        ));

        // Set frame layout as CardLayout to implement switching between different scenes
        frameLayout = new CardLayout();
        frame.setLayout(frameLayout);

        addedScenes = new HashSet<>();
        setScene(new HomeScene(new HomeControllerImpl(this, this.controller)));

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
    public int getHeight() {
        return frame.getHeight();
    }

    @Override
    public int getWidth() {
        return frame.getWidth();
    }

    @Override
    public void close() {
        System.exit(0);
    }

}
