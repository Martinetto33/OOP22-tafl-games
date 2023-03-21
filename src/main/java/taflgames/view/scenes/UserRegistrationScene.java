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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import taflgames.view.limiter.Limiter;
import taflgames.view.scenecontrollers.UserRegistrationController;

/**
 * Implementation of the user registration scene.
 */
public class UserRegistrationScene extends AbstractScene {

    private static final String USER_REGISTRATION = "User Registration";
    private static final String GO_BACK = "Go Back";
    private static final String SUBMIT = "Submit";
    private static final int SPACE = 10;
    private static final int CHARACTER_LIMIT = 50;
    /* To make the text areas resizable but smaller than the frame, a constant
     * ratio is needed.
     */
    private static final int HEIGHT_RATIO = 10;
    private static final int WIDTH_RATIO = 6;
    
    private final UserRegistrationController controller;
    private final JTextField player1NameArea;
    private final JTextField player2NameArea;
    private int verticalTextAreaSize;
    private int horizontalTextAreaSize;

    /**
     * Creates the user registration scene.
     * @param controller the scene controller
     */
    public UserRegistrationScene(final UserRegistrationController controller) {

        super(USER_REGISTRATION, Optional.of("home-background.jpeg"));

        this.controller = controller;

        final JPanel scene = super.getScene();
        final JPanel elementsPanel = new JPanel(new BorderLayout());
        this.player1NameArea = new JTextField(CHARACTER_LIMIT);
        this.player2NameArea = new JTextField(CHARACTER_LIMIT);
        
        this.setDimensions(this.controller.getViewWidth() / HEIGHT_RATIO,
            this.controller.getViewHeight() / WIDTH_RATIO);

        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
        final JButton goBackButton = new JButton(UserRegistrationScene.GO_BACK);
        final JButton submitButton = new JButton(UserRegistrationScene.SUBMIT);
        submitButton.setFont(Scene.FONT_MANAGER.getModifiedFont(Scene.BUTTON_FONT_SIZE, Font.PLAIN));
        goBackButton.setFont(Scene.FONT_MANAGER.getModifiedFont(Scene.BUTTON_FONT_SIZE, Font.PLAIN));
        goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        southPanel.add(submitButton);
        southPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        southPanel.add(goBackButton);
        southPanel.setVisible(true);

        goBackButton.addActionListener((e) -> {
            this.clearTextAreas();
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

        this.prepareTextArea(player1NameArea);
        this.prepareTextArea(player2NameArea);

        inputPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        inputPanel.add(player1NameArea);
        inputPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        inputPanel.add(player2NameArea);

        scene.add(inputPanel, BorderLayout.SOUTH);
    }

    private void prepareTextArea(JTextField text) {
        text.setColumns(50);
        text.setPreferredSize(new Dimension(this.horizontalTextAreaSize, this.verticalTextAreaSize));
        text.setFont(Scene.FONT_MANAGER.getModifiedFont(Scene.BUTTON_FONT_SIZE, Font.PLAIN));
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setDocument(new Limiter(CHARACTER_LIMIT));
    }

    public void setDimensions(int x, int y) {
        this.horizontalTextAreaSize = x;
        this.verticalTextAreaSize = y;
    }

    private void clearTextAreas() {
        this.player1NameArea.setText("");
        this.player2NameArea.setText("");
    }
}
