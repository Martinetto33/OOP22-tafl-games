package taflgames.view.scenes;

import java.util.Optional;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import taflgames.view.View;

/**
 * Implementation of the user registration scene.
 */
public class UserRegistrationScene extends AbstractScene {

    private static final String USER_REGISTRATION = "User Registration";
    private static final String GO_BACK = "Go Back";

    /**
     * Creates the user registration scene.
     * @param view the view that displays the scene
     */
    public UserRegistrationScene(final View view) {

        super(USER_REGISTRATION, Optional.of("home-background.jpeg"));

        final JPanel scene = super.getScene();

        final JPanel elementsPanel = new JPanel(new BorderLayout());

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);
        southPanel.setBackground(new Color(255, 255, 255, 0));

        goBackButton.addActionListener((e) -> {
            view.setScene(new GameChoiceScene(view));
        });

        elementsPanel.add(southPanel);

        scene.add(elementsPanel);
    }

}
