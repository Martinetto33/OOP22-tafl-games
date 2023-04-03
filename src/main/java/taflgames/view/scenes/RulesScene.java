package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
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
    private static final String BASE_LOCATION = "taflgames/rules/";
    private static final String GO_BACK = "Go Back";
    private static final Insets DEFAULT_INSETS = new Insets(20, 20, 20, 20);

    private final RulesDisplayController controller;

    /**
     * Creates the rules scene.
     * @param controller the scene controller
     */
    public RulesScene(final RulesDisplayController controller) {

        super(RULES, Optional.of(BG_FILENAME));

        this.controller = controller;

        final JPanel scene = super.getScene();
        scene.setLayout(new BorderLayout());
        scene.setBorder(new EmptyBorder(DEFAULT_INSETS));

        final JEditorPane editor = new JEditorPane();
        final HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
        final HTMLDocument htmlDocument = (HTMLDocument) htmlEditorKit.createDefaultDocument();
        htmlDocument.setBase(ClassLoader.getSystemResource(BASE_LOCATION));
        editor.setEditorKit(htmlEditorKit);
        try {
            editor.read(
                this.controller.getRulesFileStream(), 
                htmlDocument
            );
        } catch (final IOException ex) {
            editor.setText("Error: could not load rules document.");
        }
        editor.setEditable(false);
        final JScrollPane scrollPane = new JScrollPane(editor);
        scene.add(scrollPane, BorderLayout.CENTER);

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(TRANSPARENT);

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        scene.add(southPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update() {
        // There is no scene update for the rules scene currently (other than the scene switching).
    }

}
