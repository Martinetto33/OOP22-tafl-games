package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import taflgames.view.fontmanager.FontManager;
import taflgames.view.scenecontrollers.HomeController;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Implementation of the home scene.
 */
public class HomeScene extends AbstractScene {

    private static final String HOME = "Home";
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final String BG_FILENAME = "home-background.jpeg";
    private static final String GAME_TITLE = "TAFL GAMES";
    private static final int TITLE_SIZE = 100;
    private static final String PLAY = "Play";
    private static final String EXIT = "Exit";
    private static final String HIGH_SCORE = "High Score";
    private static final double BTN_HEIGHT_PROP = 0.10;
    private static final double BTN_WIDTH_PROP = 0.45;
    private static final Insets DEFAULT_INSETS = new Insets(20, 20, 20, 20);
    private static final int WIDTH_RATIO = 200;
    private static final int HEIGHT_RATIO = 200;

    private final HomeController controller;
    private final Dimension buttonDimension;

    /**
     * Creates the home scene.
     * @param controller the scene controller
     */
    public HomeScene(final HomeController controller) {

        super(HOME, Optional.of(BG_FILENAME));

        this.controller = controller;
        this.buttonDimension = new Dimension((int) (this.controller.getViewWidth() / WIDTH_RATIO),
                                            (int) (this.controller.getViewHeight() / HEIGHT_RATIO));

        final JPanel scene = super.getScene();
        scene.setLayout(new BorderLayout());
        scene.setBorder(new EmptyBorder(DEFAULT_INSETS));

        final GridBagConstraints gbc = new GridBagConstraints();
        final FontManager fontManager = new FontManager();

        final JPanel titlePanel = new JPanel();
        final JLabel titleLabel = new JLabel(GAME_TITLE);
        titleLabel.setFont(fontManager.getModifiedFont(TITLE_SIZE, Font.PLAIN));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.setBackground(TRANSPARENT);
        titlePanel.add(titleLabel);

        final JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(TRANSPARENT);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = DEFAULT_INSETS;
        gbc.ipadx = (int) (this.controller.getViewWidth() * BTN_WIDTH_PROP);
        gbc.ipady = (int) (this.controller.getViewHeight() * BTN_HEIGHT_PROP);
        final JButton playButton = new JButton(PLAY);
        playButton.setFont(AbstractScene.getDefaultFont());
        this.setButtonFixedDimension(playButton);
        buttonsPanel.add(playButton, gbc);
        final JButton exitButton = new JButton(EXIT);
        exitButton.setFont(AbstractScene.getDefaultFont());
        this.setButtonFixedDimension(exitButton);
        buttonsPanel.add(exitButton, gbc);

        this.addHighScoreButton(buttonsPanel, gbc);

        playButton.addActionListener((e) -> {
            this.controller.goToNextScene();
        });

        exitButton.addActionListener((e) -> this.controller.close());

        scene.add(titlePanel, BorderLayout.NORTH);
        scene.add(buttonsPanel, BorderLayout.CENTER);
    }

    private void addHighScoreButton(final JPanel buttonPanel, final GridBagConstraints gbc) {
        final JButton highScoreButton = new JButton(HIGH_SCORE);
        highScoreButton.setFont(Scene.FONT_MANAGER.getButtonFont());
        highScoreButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent arg0) {
                controller.goToHighScoreScene();
            }

        });
        this.setButtonFixedDimension(highScoreButton);
        buttonPanel.add(highScoreButton, gbc);
    }

    private void setButtonFixedDimension(final JButton button) {
        button.setPreferredSize(this.buttonDimension);
    }

    @Override
    public void update() {
        // There is no scene update for the home scene currently (other than the scene switching).
    }

}
