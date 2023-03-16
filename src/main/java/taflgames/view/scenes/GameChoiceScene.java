package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Optional;

import javax.swing.JPanel;

import taflgames.view.View;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Implementation of the game choice scene.
 */
public class GameChoiceScene extends AbstractScene {

    private static final String GAME_CHOICE = "Game Choice";
    private static final String HEADER = "Choose the game mode";
    private static final String PLAY_CLASSIC_MODE = "Play Classic Mode";
    private static final String PLAY_VARIANT_MODE = "Play Variant Mode";
    private static final String GO_BACK = "Go Back";

    /**
     * Creates the game choice scene.
     * @param view the view that displays the scene
     */
    public GameChoiceScene(final View view) {

        super(GAME_CHOICE, Optional.of("home-background.jpeg"));

        final JPanel scene = super.getScene();

        final JPanel elementsPanel = new JPanel(new BorderLayout());
        elementsPanel.setBackground(new Color(255, 255, 255, 0));

        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        final JLabel headerLabel = new JLabel(HEADER);
        headerLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        headerPanel.add(headerLabel);

        final JPanel playButtonsPanel = new JPanel();
        playButtonsPanel.setLayout(new BoxLayout(playButtonsPanel, BoxLayout.Y_AXIS));
        final JButton playClassicButton = new JButton(PLAY_CLASSIC_MODE);
        playClassicButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        playButtonsPanel.add(playClassicButton);
        final JButton playVariantButton = new JButton(PLAY_VARIANT_MODE);
        playVariantButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        playButtonsPanel.setBackground(new Color(255, 255, 255, 0));
        playButtonsPanel.add(playVariantButton);

        playClassicButton.addActionListener((e) -> {
            view.getController().createClassicModeMatch();
            view.setScene(new UserRegistrationScene(view));
        });

        playVariantButton.addActionListener((e) -> {
            view.getController().createVariantModeMatch();
            view.setScene(new UserRegistrationScene(view));
        });

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(new Color(255, 255, 255, 0));

        goBackButton.addActionListener((e) -> {
            view.setScene(new HomeScene(view));
        });

        /*
         * TO DO: add game rules
         */

        elementsPanel.add(headerPanel, BorderLayout.NORTH);
        elementsPanel.add(playButtonsPanel, BorderLayout.CENTER);
        elementsPanel.add(southPanel, BorderLayout.SOUTH);
        
        scene.add(elementsPanel);
    }

}
