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

    private final GameChoiceController controller;

    /**
     * Creates the game choice scene.
     * @param controller the scene controller
     */
    public GameChoiceScene(final GameChoiceController controller) {

        super(GAME_CHOICE, Optional.of(BG_FILENAME));

        this.controller = controller;

        final JPanel scene = super.getScene();

        final GridBagConstraints outerGBC = new GridBagConstraints();

        final JPanel elementsPanel = new JPanel(new GridBagLayout());
        elementsPanel.setBackground(TRANSPARENT);

        final JPanel headerPanel = new JPanel(new GridBagLayout());
        final JLabel headerLabel = new JLabel(HEADER);
        outerGBC.gridwidth = GridBagConstraints.REMAINDER;
        outerGBC.anchor = GridBagConstraints.NORTH;
        headerLabel.setFont(AbstractScene.getDefaultFont());
        headerLabel.setForeground(Color.WHITE);
        headerPanel.setBackground(TRANSPARENT);
        headerPanel.add(headerLabel);
        elementsPanel.add(headerPanel, outerGBC);

        final JPanel playButtonsPanel = new JPanel(new GridBagLayout());
        outerGBC.anchor = GridBagConstraints.CENTER;
        outerGBC.fill = GridBagConstraints.HORIZONTAL;
        outerGBC.insets = new Insets(10, 0, 10, 0);
        playButtonsPanel.setBackground(TRANSPARENT);

        final GridBagConstraints innerGBC = new GridBagConstraints();
        innerGBC.gridwidth = GridBagConstraints.REMAINDER;
        innerGBC.insets = new Insets(0, 0, 0, 0);

        final JPanel playClassicPanel = new JPanel(new GridBagLayout());
        playClassicPanel.setBackground(TRANSPARENT);

        final JButton playClassicButton = new JButton(PLAY_CLASSIC_MODE);
        playClassicButton.setFont(AbstractScene.getDefaultFont());
        playClassicButton.setPreferredSize(new Dimension(
            (int) (this.controller.getViewWidth() * MAIN_BTN_WIDTH_PROP),
            (int) (this.controller.getViewHeight() * MAIN_BTN_HEIGHT_PROP)
        ));
        playClassicPanel.add(playClassicButton, innerGBC);

        final JButton classicRulesButton = new JButton(SEE_RULES);
        classicRulesButton.setFont(AbstractScene.getDefaultFont());
        playClassicPanel.add(classicRulesButton, innerGBC);

        playButtonsPanel.add(playClassicPanel, outerGBC);

        final JPanel playVariantPanel = new JPanel(new GridBagLayout());
        playVariantPanel.setBackground(TRANSPARENT);

        final JButton playVariantButton = new JButton(PLAY_VARIANT_MODE);
        playVariantButton.setFont(AbstractScene.getDefaultFont());
        playVariantButton.setPreferredSize(new Dimension(
            (int) (this.controller.getViewWidth() * MAIN_BTN_WIDTH_PROP),
            (int) (this.controller.getViewHeight() * MAIN_BTN_HEIGHT_PROP)
        ));
        playVariantPanel.add(playVariantButton, innerGBC);

        final JButton variantRulesButton = new JButton(SEE_RULES);
        variantRulesButton.setFont(AbstractScene.getDefaultFont());
        playVariantPanel.add(variantRulesButton, innerGBC);

        playButtonsPanel.add(playVariantPanel, outerGBC);

        playClassicButton.addActionListener((e) -> {
            this.controller.createClassicModeMatch();
            this.controller.goToNextScene();
        });

        playVariantButton.addActionListener((e) -> {
            this.controller.createVariantModeMatch();
            this.controller.goToNextScene();
        });

        classicRulesButton.addActionListener((e) -> {
            /*
             * TO DO
             */
        });

        variantRulesButton.addActionListener((e) -> {
            /*
             * TO DO
             */
        });

        elementsPanel.add(playButtonsPanel, outerGBC);

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(TRANSPARENT);

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        elementsPanel.add(southPanel, outerGBC);

        scene.add(elementsPanel);
    }

}
