package taflgames.view.scenes;

import java.util.Optional;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import taflgames.view.fontmanager.FontManager;
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
    private static final int CHARACTER_LIMIT = 20;
    private static final int CHARACTER_SIZE_FOR_LABELS = 20;
    private static final int COLUMNS_NUMBER = 50;
    /* a constant
     * ratio is needed to make the text areas resizable but smaller than the frame.
     */
    private static final int HEIGHT_RATIO = 10;
    private static final int WIDTH_RATIO = 10;

    private static final int LABEL_WIDTH = 120;
    private static final int LABEL_HEIGHT = 60;

    private final UserRegistrationController controller;
    private final FontManager fontManager = AbstractScene.getFontManager();
    private final JTextField attackerNameTextField;
    private final JTextField defenderNameTextField;
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
        this.attackerNameTextField = new JTextField(CHARACTER_LIMIT);
        this.defenderNameTextField = new JTextField(CHARACTER_LIMIT);

        this.setDimensions(this.controller.getViewWidth() / WIDTH_RATIO,
            this.controller.getViewHeight() / HEIGHT_RATIO);

        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
        final JButton goBackButton = new JButton(UserRegistrationScene.GO_BACK);
        final JButton submitButton = new JButton(UserRegistrationScene.SUBMIT);
        submitButton.setFont(fontManager.getButtonFont());
        goBackButton.setFont(fontManager.getButtonFont());
        goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        southPanel.add(submitButton);
        southPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        southPanel.add(goBackButton);
        southPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        southPanel.setVisible(true);

        goBackButton.addActionListener((e) -> {
            this.clearTextAreas();
            this.controller.goToPreviousScene();
        });

        this.attachSubmitListener(submitButton);

        southPanel.setBackground(AbstractScene.getTransparency());
        elementsPanel.setBackground(AbstractScene.getTransparency());

        elementsPanel.add(southPanel);

        this.constructInputLabels(elementsPanel);

        scene.add(elementsPanel);

    }

    /*
     * Attaches a listener to the Submit button; the listener will
     * check if there are valid usernames to send to the controller.
     * @param submitButton the button to attach the listener to
     */
    private void attachSubmitListener(final JButton submitButton) {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                if (areUsernamesValid()) {
                    controller.registerMatchResult(attackerNameTextField.getText(),
                    defenderNameTextField.getText());
                    controller.goToNextScene();
                } else {
                    JOptionPane.showMessageDialog(getScene(),
                    "Players' nicknames cannot be blank.", "Whoops", JOptionPane.OK_OPTION);
                }
            }
        });
    }

    /*
     * Checks if the usernames are valid, i.e. they are not
     * empty strings.
     * @returns true if the usernames are valid, false otherwise.
     */
    private boolean areUsernamesValid() {
        return this.attackerNameTextField.getText().length() > 0
            && this.defenderNameTextField.getText().length() > 0;
    }

    /* Builds the area in which the user can insert player names. */
    private void constructInputLabels(final JPanel scene) {
        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        inputPanel.setBackground(AbstractScene.getTransparency());

        this.prepareTextArea(this.attackerNameTextField);
        this.prepareTextArea(this.defenderNameTextField);


        this.createLabel(inputPanel, "Insert attacker's nickname: ");
        inputPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        inputPanel.add(attackerNameTextField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        this.createLabel(inputPanel, "Insert defender's nickname: ");
        inputPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        inputPanel.add(defenderNameTextField);

        scene.add(inputPanel, BorderLayout.SOUTH);
    }

    private void prepareTextArea(final JTextField text) {
        text.setColumns(COLUMNS_NUMBER);
        text.setPreferredSize(new Dimension(this.horizontalTextAreaSize, this.verticalTextAreaSize));
        text.setFont(fontManager.getButtonFont());
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setDocument(new Limiter(CHARACTER_LIMIT));
    }

    /**
     * Resizes the text areas.
     * @param x horizontal size.
     * @param y vertical size.
     */
    public final void setDimensions(final int x, final int y) {
        this.horizontalTextAreaSize = x;
        this.verticalTextAreaSize = y;
    }

    /*
     * Clears the fields.
     */
    private void clearTextAreas() {
        this.attackerNameTextField.setText("");
        this.defenderNameTextField.setText("");
    }

    private void createLabel(final JPanel panel, final String labelContent) {
        final JLabel label = new JLabel();
        label.setText(labelContent);
        label.setFont(fontManager.getModifiedFont(UserRegistrationScene.CHARACTER_SIZE_FOR_LABELS, Font.ITALIC));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        super.addComponentBackground(panel, UserRegistrationScene.LABEL_WIDTH, UserRegistrationScene.LABEL_HEIGHT, label);
    }

}
