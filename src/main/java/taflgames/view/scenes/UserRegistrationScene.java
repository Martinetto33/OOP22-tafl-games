package taflgames.view.scenes;

import java.util.Optional;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import taflgames.view.fontManager.FontManager;
import taflgames.view.scenecontrollers.UserRegistrationController;

/**
 * Implementation of the user registration scene.
 */
public class UserRegistrationScene extends AbstractScene {

    private static final String USER_REGISTRATION = "User Registration";
    private static final String GO_BACK = "Go Back";
    private static final String SUBMIT = "Submit";
    private static final int SPACE = 10;
    private static final int TEXT_AREA_SIZE_VERTICAL = 140;
    private static final int TEXT_AREA_SIZE_HORIZONTAL = 100;

    private final UserRegistrationController controller;

    /**
     * Creates the user registration scene.
     * @param controller the scene controller
     */
    public UserRegistrationScene(final UserRegistrationController controller) {

        super(USER_REGISTRATION, Optional.of("home-background.jpeg"));

        this.controller = controller;

        final FontManager fontManager = new FontManager();

        final JPanel scene = super.getScene();
        final JPanel elementsPanel = new JPanel(new BorderLayout());

        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
        final JButton goBackButton = new JButton(UserRegistrationScene.GO_BACK);
        final JButton submitButton = new JButton(UserRegistrationScene.SUBMIT);
        submitButton.setFont(fontManager.getModifiedFont(Scene.BUTTON_FONT_SIZE, Font.PLAIN));
        goBackButton.setFont(fontManager.getModifiedFont(Scene.BUTTON_FONT_SIZE, Font.PLAIN));
        goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        southPanel.add(submitButton);
        southPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        southPanel.add(goBackButton);
        southPanel.setVisible(true);

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        southPanel.setBackground(Scene.TRANSPARENT);
        elementsPanel.setBackground(Scene.TRANSPARENT);

        elementsPanel.add(southPanel);

        this.constructInputLabels(elementsPanel);

        scene.add(elementsPanel);

    }

    private JButton attachSubmitListener(final JButton submitButton) {
        submitButton.addActionListener(null);
        return submitButton;
    }

    /* Builds the area in which the user can insert player names. */
    private void constructInputLabels(final JPanel scene) {
        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        inputPanel.setBackground(Scene.TRANSPARENT);
        final JTextArea player1NameArea = new JTextArea();
        final JTextArea player2NameArea = new JTextArea();

        /* TODO: set text areas to be bigger and have a character limit */
        player1NameArea.setSize(new Dimension(UserRegistrationScene.TEXT_AREA_SIZE_VERTICAL, 
        UserRegistrationScene.TEXT_AREA_SIZE_HORIZONTAL));
        player1NameArea.setColumns(50);
        player2NameArea.setSize(new Dimension(UserRegistrationScene.TEXT_AREA_SIZE_VERTICAL, 
        UserRegistrationScene.TEXT_AREA_SIZE_HORIZONTAL));
        player2NameArea.setColumns(50);
        player2NameArea.setLineWrap(false);

        inputPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        inputPanel.add(player1NameArea);
        inputPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        inputPanel.add(player2NameArea);

        scene.add(inputPanel, BorderLayout.SOUTH);
    }
}
