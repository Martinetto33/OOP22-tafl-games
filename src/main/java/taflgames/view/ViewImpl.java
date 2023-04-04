package taflgames.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import taflgames.controller.Controller;
import taflgames.controller.ControllerImpl;
import taflgames.view.scenecontrollers.HomeSceneControllerImpl;
import taflgames.view.scenes.HomeScene;
import taflgames.view.scenes.Scene;

/**
 * This class implements the view of the application.
 */
public final class ViewImpl implements View {

    private static final String FRAME_TITLE = "Tafl Games";
    private static final double DEFAULT_FRAME_WIDTH_PROP = 0.67;
    private static final double DEFAULT_FRAME_HEIGHT_PROP = 0.67;
    private static final double MIN_FRAME_WIDTH_PROP = 0.60;
    private static final double MIN_FRAME_HEIGHT_PROP = 0.60;

    private final JFrame frame;
    private final CardLayout frameLayout;
    private final Dimension defaultFrameSize;
    private Scene currentScene;

    /**
     * Sets up the view.
     */
    public ViewImpl() {

        frame = new JFrame(FRAME_TITLE);

        // Set the frame size
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int screenWidth = (int) screen.getWidth();
        final int screenHeight = (int) screen.getHeight();
        frame.setSize(new Dimension(
            (int) (screenWidth * DEFAULT_FRAME_WIDTH_PROP),
            (int) (screenHeight * DEFAULT_FRAME_HEIGHT_PROP)
        ));
        defaultFrameSize = frame.getSize();
        frame.setMinimumSize(new Dimension(
            (int) (screenWidth * MIN_FRAME_WIDTH_PROP),
            (int) (screenHeight * MIN_FRAME_HEIGHT_PROP)
        ));

        // Set frame layout as CardLayout to implement switching between different scenes
        frameLayout = new CardLayout();
        frame.setLayout(frameLayout);

        final Controller controller = new ControllerImpl(this);
        setScene(new HomeScene(new HomeSceneControllerImpl(this, controller)));

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);  // Let the OS decide about the positioning of the frame
        frame.setVisible(true);
    }

    @Override
    public void setScene(final Scene scene) {
        final String sceneName = scene.getSceneName();
        frame.getContentPane().add(scene.getScene(), sceneName);
        frameLayout.show(frame.getContentPane(), sceneName);
        this.currentScene = scene;
    }

    @Override
    public int getHeight() {
        return (int) defaultFrameSize.getHeight();
    }

    @Override
    public int getWidth() {
        return (int) defaultFrameSize.getWidth();
    }

    @Override
    public void update() {
        this.currentScene.update();
    }

    @Override
    public void close() {
        System.exit(0);
    }

}
