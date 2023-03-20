package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

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

    private final RulesDisplayController controller;

    public RulesScene(final RulesDisplayController controller) {

        super(RULES, Optional.of(BG_FILENAME));

        this.controller = controller;

        final JPanel scene = super.getScene();
        
        final JPanel elementsPanel = new JPanel(new BorderLayout());
        elementsPanel.setBackground(TRANSPARENT);

        final JEditorPane editor = new JEditorPane();
        final HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        editor.setEditorKit(new HTMLEditorKit());
        try {
            editor.read(
                this.controller.getRulesFileStream(), 
                (HTMLDocument) htmlEditorKit.createDefaultDocument()
            );
        } catch (final IOException ex) {
            editor.setText(ex.getMessage());
        }
        editor.setEditable(false);
        elementsPanel.add(editor, BorderLayout.CENTER);

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(TRANSPARENT);

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        elementsPanel.add(southPanel, BorderLayout.SOUTH);

        scene.add(elementsPanel);
        
    }

}
