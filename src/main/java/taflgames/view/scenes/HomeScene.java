package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import taflgames.view.fontmanager.FontManager;
import taflgames.view.scenecontrollers.HomeSceneController;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Implementation of the home scene.
 */
public class HomeScene extends AbstractScene {

    private static final String HOME = "Home";
    private static final String GAME_TITLE = "TAFL GAMES";
    private static final int TITLE_SIZE = 100;
    private static final String PLAY = "Play";
    private static final String EXIT = "Exit";
    private static final String HIGH_SCORE = "High Score";
    private static final double BTN_HEIGHT_PROP = 0.10;
    private static final double BTN_WIDTH_PROP = 0.45;
    private static final int WIDTH_RATIO = 200;
    private static final int HEIGHT_RATIO = 200;

    private final FontManager fontManager = AbstractScene.getFontManager();

    private final HomeSceneController controller;

    /**
     * Creates the home scene.
     * @param controller the scene controller
     */
    public HomeScene(final HomeSceneController controller) {

        super(HOME);

        this.controller = controller;
        final Dimension buttonDimension = new Dimension(
            (int) (this.controller.getViewWidth() / WIDTH_RATIO),
            (int) (this.controller.getViewHeight() / HEIGHT_RATIO)
        );

        final JPanel scene = super.getScene();
        scene.setLayout(new BorderLayout());
        scene.setBorder(new EmptyBorder(AbstractScene.getDefaultInsets()));

        final GridBagConstraints gbc = new GridBagConstraints();

        final JPanel titlePanel = new JPanel();
        final JLabel titleLabel = new JLabel(GAME_TITLE);
        titleLabel.setFont(fontManager.getModifiedFont(TITLE_SIZE, Font.PLAIN));
        titleLabel.setForeground(AbstractScene.getLabelTextColor());
        titlePanel.setBackground(AbstractScene.getTransparency());
        titlePanel.add(titleLabel);

        final JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(AbstractScene.getTransparency());
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = AbstractScene.getDefaultInsets();
        gbc.ipadx = (int) (this.controller.getViewWidth() * BTN_WIDTH_PROP);
        gbc.ipady = (int) (this.controller.getViewHeight() * BTN_HEIGHT_PROP);

        final JButton playButton = new JButton(PLAY);
        playButton.setFont(fontManager.getButtonFont());
        playButton.setPreferredSize(buttonDimension);
        buttonsPanel.add(playButton, gbc);

        final JButton highScoreButton = new JButton(HIGH_SCORE);
        highScoreButton.setFont(fontManager.getButtonFont());
        highScoreButton.setPreferredSize(buttonDimension);
        buttonsPanel.add(highScoreButton, gbc);

        final JButton exitButton = new JButton(EXIT);
        exitButton.setFont(fontManager.getButtonFont());
        exitButton.setPreferredSize(buttonDimension);
        buttonsPanel.add(exitButton, gbc);

        playButton.addActionListener((e) -> this.controller.goToNextScene());

        highScoreButton.addActionListener((e) -> this.controller.goToHighScoreScene());

        exitButton.addActionListener((e) -> this.controller.close());

        scene.add(titlePanel, BorderLayout.NORTH);
        scene.add(buttonsPanel, BorderLayout.CENTER);
    }

}
