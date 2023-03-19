package taflgames.view.scenes;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import java.util.Optional;

import taflgames.view.scenecontrollers.RulesDisplayController;

/**
 * Implementation of the scene that shows the rules of the game modes.
 */
public class RulesScene extends AbstractScene {
    
    private static final String RULES = "Rules";
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final String BG_FILENAME = "home-background.jpeg";
    private static final String GO_BACK = "Go Back";

    private RulesDisplayController controller;

    public RulesScene(final RulesDisplayController controller) {

        super(RULES, Optional.of(BG_FILENAME));

        this.controller = controller;

        final JPanel scene = super.getScene();
        
        final JPanel elementsPanel = new JPanel();
        elementsPanel.setBackground(TRANSPARENT);

        final JEditorPane editor = new JEditorPane();
        elementsPanel.add(editor);

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(TRANSPARENT);

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        elementsPanel.add(southPanel);

        scene.add(elementsPanel);
        
    }

}
