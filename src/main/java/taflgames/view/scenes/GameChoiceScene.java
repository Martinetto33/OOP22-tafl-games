package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import taflgames.view.fontmanager.FontManager;
import taflgames.view.scenecontrollers.GameChoiceSceneController;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Implementation of the game choice scene.
 */
public class GameChoiceScene extends AbstractScene {

    private static final String GAME_CHOICE = "Game Choice";
    private static final String HEADER = "Choose the game mode";
    private static final float HEADER_FONT_SIZE = 30.0f;
    private static final String PLAY_CLASSIC_MODE = "Play Classic Mode";
    private static final String PLAY_VARIANT_MODE = "Play Variant Mode";
    private static final String GO_BACK = "Go Back";
    private static final String SEE_RULES = "See Rules";
    private static final double MAIN_BTN_HEIGHT_PROP = 0.075;
    private static final double MAIN_BTN_WIDTH_PROP = 0.33;
    private static final double MINOR_BTN_HEIGHT_PROP = 0.025;
    private static final double MINOR_BTN_WIDTH_PROP = 0.10;

    private final FontManager fontManager = AbstractScene.getFontManager();

    private final GameChoiceSceneController controller;

    /**
     * Creates the game choice scene.
     * @param controller the scene controller
     */
    public GameChoiceScene(final GameChoiceSceneController controller) {

        super(GAME_CHOICE);

        this.controller = controller;

        final JPanel scene = super.getScene();
        scene.setLayout(new BorderLayout());
        scene.setBorder(new EmptyBorder(AbstractScene.getDefaultInsets()));

        final GridBagConstraints gbc = new GridBagConstraints();

        final JPanel headerPanel = new JPanel();
        final JLabel headerLabel = new JLabel(HEADER);
        final Font headerFont = fontManager.getModifiedFont(HEADER_FONT_SIZE, Font.PLAIN);
        headerLabel.setFont(headerFont);
        headerLabel.setForeground(Color.WHITE);
        headerPanel.setBackground(AbstractScene.getTransparency());
        headerPanel.add(headerLabel);
        scene.add(headerPanel, BorderLayout.NORTH);

        final JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(AbstractScene.getTransparency());

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = AbstractScene.getDefaultInsets();
        gbc.ipadx = (int) (this.controller.getViewWidth() * MAIN_BTN_WIDTH_PROP);
        gbc.ipady = (int) (this.controller.getViewHeight() * MAIN_BTN_HEIGHT_PROP);
        final JButton playClassicButton = new JButton(PLAY_CLASSIC_MODE);
        playClassicButton.setFont(fontManager.getButtonFont());
        buttonsPanel.add(playClassicButton, gbc);
        final JButton playVariantButton = new JButton(PLAY_VARIANT_MODE);
        playVariantButton.setFont(fontManager.getButtonFont());
        buttonsPanel.add(playVariantButton, gbc);

        final JButton seeRulesButton = new JButton(SEE_RULES);
        seeRulesButton.setFont(fontManager.getButtonFont());
        gbc.ipadx = (int) (this.controller.getViewWidth() * MINOR_BTN_WIDTH_PROP);
        gbc.ipady = (int) (this.controller.getViewHeight() * MINOR_BTN_HEIGHT_PROP);
        buttonsPanel.add(seeRulesButton, gbc);

        playClassicButton.addActionListener((e) -> {
            try {
                this.controller.createClassicModeMatch();
                this.controller.goToNextScene();
            } catch (final IOException ex) {
                JOptionPane.showMessageDialog(scene, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        playVariantButton.addActionListener((e) -> {
            try {
                this.controller.createVariantModeMatch();
                this.controller.goToNextScene();
            } catch (final IOException ex) {
                JOptionPane.showMessageDialog(scene, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        seeRulesButton.addActionListener((e) -> {
            this.controller.goToRulesScene();
        });

        scene.add(buttonsPanel, BorderLayout.CENTER);

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        goBackButton.setFont(fontManager.getButtonFont());
        southPanel.add(goBackButton);
        southPanel.setBackground(AbstractScene.getTransparency());

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        scene.add(southPanel, BorderLayout.SOUTH);
    }

}
