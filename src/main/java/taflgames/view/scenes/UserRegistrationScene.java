package taflgames.view.scenes;

import java.util.Optional;

import javax.swing.BoxLayout;
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

        super(USER_REGISTRATION, Optional.of("home-background.jpg"));

        final JPanel scenePanel = super.getScene();
        scenePanel.setLayout(new BoxLayout(scenePanel, BoxLayout.Y_AXIS));

        final JPanel southPanel = new JPanel();
        final JButton goBackButton = new JButton(GO_BACK);
        southPanel.add(goBackButton);

        goBackButton.addActionListener((e) -> {
            view.setScene(new GameChoiceScene(view));
        });

        scenePanel.add(southPanel);
    }

}
