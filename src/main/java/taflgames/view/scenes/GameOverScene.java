package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Optional;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import taflgames.view.fontmanager.FontManager;
import taflgames.view.scenecontrollers.GameOverController;

/**
 * The screen that appears at the end of the game.
 */
public class GameOverScene extends AbstractScene {

    private static final String GAME_OVER = "Game Over";
    private static final String MAIN_MENU = "Main menu";
    private static final String REGISTER_RESULT = "Register result";
    private static final int MAIN_FONT_SIZE = 60;
    private final JButton mainMenuButton;
    private final JButton registerResultButton;

    private GameOverController controller;

    /**
     * Builds a new GameOverScene.
     * @param controller the scene controller
     */
    public GameOverScene(final GameOverController controller) {

        super(GameOverScene.GAME_OVER, Optional.of("home-background.jpeg"));

        this.controller = controller;

        final JPanel scene = super.getScene();

        final JPanel elementsPanel = new JPanel(new BorderLayout());

        final JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        final JLabel gameOverLabel = new JLabel(GameOverScene.GAME_OVER);
        gameOverLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        /* Using a unified Font would make it easier to change the aspect of the GUI. AbstractScene
         * was modified in a way that provides a common Font which all components could use.
         */

        gameOverLabel.setFont(Scene.FONT_MANAGER.getModifiedFont(GameOverScene.MAIN_FONT_SIZE, Font.PLAIN));
        gameOverLabel.setForeground(Scene.LABEL_FOREGROUND_COLOR);
        gameOverPanel.add(gameOverLabel);

        final JPanel buttonsPanel = new JPanel();
        this.mainMenuButton = new JButton(GameOverScene.MAIN_MENU);
        this.mainMenuButton.setFont(Scene.FONT_MANAGER.getModifiedFont(Scene.BUTTON_FONT_SIZE, Font.PLAIN));
        this.registerResultButton = new JButton(GameOverScene.REGISTER_RESULT);
        this.registerResultButton.setFont(Scene.FONT_MANAGER.getModifiedFont(Scene.BUTTON_FONT_SIZE, Font.PLAIN));

        /*Adding listeners */
        this.createMainMenuActionListener(this.controller);
        this.createRegisterResultActionListener(this.controller);

        buttonsPanel.add(this.mainMenuButton);
        buttonsPanel.add(this.registerResultButton);

        /*In order to have a transparent panel, all panels have to have the same background colour */
        buttonsPanel.setBackground(Scene.TRANSPARENT);
        gameOverPanel.setBackground(Scene.TRANSPARENT);
        elementsPanel.setBackground(Scene.TRANSPARENT);

        elementsPanel.add(gameOverPanel, BorderLayout.NORTH);
        elementsPanel.add(buttonsPanel, BorderLayout.CENTER);

        /* This is a way to move the Game Over label down towards the center of the screen,
         * but seems a bit too rigid to use.
         */
        //scene.add(Box.createRigidArea(new Dimension(0, controller.getViewHeight() / 2)));
        scene.add(elementsPanel, BorderLayout.CENTER);
    }

    private void createMainMenuActionListener(final GameOverController controller) {
        this.mainMenuButton.addActionListener(e -> controller.goToNextScene());
    }

    /*If we respect the plan made in the analysis phase, the result registration is optional
     * and only occurs at the end of a match.
     */
    private void createRegisterResultActionListener(final GameOverController controller) {
        this.registerResultButton.addActionListener(e -> controller.goToRegistrationScene());
    }
}
