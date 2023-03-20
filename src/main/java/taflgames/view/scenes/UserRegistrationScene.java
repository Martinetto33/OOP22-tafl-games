package taflgames.view.scenes;

import java.util.Optional;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import taflgames.view.scenecontrollers.UserRegistrationController;

/**
 * Implementation of the user registration scene.
 */
public class UserRegistrationScene extends AbstractScene {

    private static final String USER_REGISTRATION = "User Registration";
    private static final String GO_BACK = "Go Back";

    private final UserRegistrationController controller;

    /**
     * Creates the user registration scene.
     * @param controller the scene controller
     */
    public UserRegistrationScene(final UserRegistrationController controller) {

        super(USER_REGISTRATION, Optional.of("home-background.jpeg"));

        this.controller = controller;

        final JPanel scene = super.getScene();

        final JPanel elementsPanel = new JPanel(new BorderLayout());

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(new Color(0, 0, 0, 0));

        goBackButton.addActionListener((e) -> {
            this.controller.goToPreviousScene();
        });

        elementsPanel.add(southPanel);

        scene.add(elementsPanel);
    }

}
