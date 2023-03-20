package taflgames.view.scenes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.JPanel;

import taflgames.view.scenecontrollers.GameChoiceController;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * Implementation of the game choice scene.
 */
public class GameChoiceScene extends AbstractScene {

    private static final String GAME_CHOICE = "Game Choice";
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final String BG_FILENAME = "home-background.jpeg";
    private static final String HEADER = "Choose the game mode";
    private static final String PLAY_CLASSIC_MODE = "Play Classic Mode";
    private static final String PLAY_VARIANT_MODE = "Play Variant Mode";
    private static final String GO_BACK = "Go Back";
    private static final String SEE_RULES = "See Rules";
    private static final double MAIN_BTN_HEIGHT_PROP = 0.075;
    private static final double MAIN_BTN_WIDTH_PROP = 0.33;
    private static final double MINOR_BTN_HEIGHT_PROP = 0.05;
    private static final double MINOR_BTN_WIDTH_PROP = 0.15;

    private final GameChoiceController controller;

    /**
     * Creates the game choice scene.
     * @param controller the scene controller
     */
    public GameChoiceScene(final GameChoiceController controller) {

        super(GAME_CHOICE, Optional.of(BG_FILENAME));

        this.controller = controller;

        final JPanel scene = super.getScene();

        final GridBagConstraints gbc = new GridBagConstraints();

        final JPanel elementsPanel = new JPanel(new GridBagLayout());
        elementsPanel.setBackground(TRANSPARENT);

        final JPanel headerPanel = new JPanel(new GridBagLayout());
        final JLabel headerLabel = new JLabel(HEADER);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        headerLabel.setFont(AbstractScene.getDefaultFont());
        headerLabel.setForeground(Color.WHITE);
        headerPanel.setBackground(TRANSPARENT);
        headerPanel.add(headerLabel);
        elementsPanel.add(headerPanel, gbc);

        final JPanel buttonsPanel = new JPanel(new GridBagLayout());
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);
        buttonsPanel.setBackground(TRANSPARENT);

        final JButton playClassicButton = new JButton(PLAY_CLASSIC_MODE);
        playClassicButton.setFont(AbstractScene.getDefaultFont());
        playClassicButton.setPreferredSize(new Dimension(
            (int) (this.controller.getViewWidth() * MAIN_BTN_WIDTH_PROP),
            (int) (this.controller.getViewHeight() * MAIN_BTN_HEIGHT_PROP)
        ));
        buttonsPanel.add(playClassicButton, gbc);

        final JButton playVariantButton = new JButton(PLAY_VARIANT_MODE);
        playVariantButton.setFont(AbstractScene.getDefaultFont());
        playVariantButton.setPreferredSize(new Dimension(
            (int) (this.controller.getViewWidth() * MAIN_BTN_WIDTH_PROP),
            (int) (this.controller.getViewHeight() * MAIN_BTN_HEIGHT_PROP)
        ));
        buttonsPanel.add(playVariantButton, gbc);

        final JButton seeRulesButton = new JButton(SEE_RULES);
        seeRulesButton.setFont(AbstractScene.getDefaultFont());
        seeRulesButton.setPreferredSize(new Dimension(
            (int) (this.controller.getViewWidth() * MINOR_BTN_WIDTH_PROP),
            (int) (this.controller.getViewHeight() * MINOR_BTN_HEIGHT_PROP)
        ));
        buttonsPanel.add(seeRulesButton, gbc);

        playClassicButton.addActionListener((e) -> {
            this.controller.createClassicModeMatch();
            this.controller.goToNextScene();
        });

        playVariantButton.addActionListener((e) -> {
            this.controller.createVariantModeMatch();
            this.controller.goToNextScene();
        });

        seeRulesButton.addActionListener((e) -> {
            this.controller.goToRulesScene();
        });

        elementsPanel.add(buttonsPanel, gbc);

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(TRANSPARENT);

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        elementsPanel.add(southPanel, gbc);

        scene.add(elementsPanel);
    }

}
