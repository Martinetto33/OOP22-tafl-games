package taflgames.view.scenes;

import java.awt.BorderLayout;
import java.awt.Point;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import java.util.Optional;

import taflgames.view.fontmanager.FontManager;
import taflgames.view.scenecontrollers.RulesSceneController;

/**
 * Implementation of the scene that shows the rules of the game modes.
 */
public class RulesScene extends AbstractScene {

    private static final String RULES = "Rules";
    private static final String BG_FILENAME = "home-background.jpeg";
    private static final String BASE_LOCATION = "taflgames/rules/";
    private static final String GO_BACK = "Go Back";

    private final FontManager fontManager = AbstractScene.getFontManager();

    private final RulesSceneController controller;

    /**
     * Creates the rules scene.
     * @param controller the scene controller
     */
    public RulesScene(final RulesSceneController controller) {

        super(RULES, Optional.of(BG_FILENAME));

        this.controller = controller;

        final JPanel scene = super.getScene();
        scene.setLayout(new BorderLayout());
        scene.setBorder(new EmptyBorder(AbstractScene.getDefaultInsets()));

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
        // Place the scroll bar at the top of the pane
        SwingUtilities.invokeLater(() -> scrollPane.getViewport().setViewPosition(new Point(0, 0)));
        scene.add(scrollPane, BorderLayout.CENTER);

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
