package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import taflgames.view.View;

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
    /**
     * Builds a new GameOverScene.
     * @param view the view that displays the scene
     */
    public GameOverScene(final View view) {

        super(GameOverScene.GAME_OVER);

        final JPanel scenePanel = super.getScene();
        scenePanel.setLayout(new BoxLayout(scenePanel, BoxLayout.Y_AXIS));

        final JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
        final JLabel gameOverLabel = new JLabel(GameOverScene.GAME_OVER);
        gameOverLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        gameOverLabel.setFont(new Font("Serif", Font.PLAIN, GameOverScene.MAIN_FONT_SIZE));
        gameOverPanel.add(gameOverLabel);

        final JPanel buttonsPanel = new JPanel();
        this.mainMenuButton = new JButton(GameOverScene.MAIN_MENU);
        this.registerResultButton = new JButton(GameOverScene.REGISTER_RESULT);

        buttonsPanel.add(this.mainMenuButton);
        buttonsPanel.add(this.registerResultButton);

        scenePanel.add(gameOverPanel, BorderLayout.NORTH);
        scenePanel.add(buttonsPanel, BorderLayout.CENTER);
    }

    private void createMainMenuActionListener() {

    }
}
