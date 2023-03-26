package taflgames.view.scenes;

import java.util.Optional;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import taflgames.view.limiter.Limiter;
import taflgames.view.scenecontrollers.UserRegistrationController;

/**
 * Implementation of the user registration scene.
 */
public class UserRegistrationScene extends AbstractScene {

    private static final String SEP = System.getProperty("file.separator");
    private static final String ROOT = "taflgames" + SEP;
    private static final String IMAGE_NAME = "wooden-plank.jpg";

    private static final String USER_REGISTRATION = "User Registration";
    private static final String GO_BACK = "Go Back";
    private static final String SUBMIT = "Submit";
    private static final int SPACE = 10;
    private static final int CHARACTER_LIMIT = 50;
    private static final int CHARACTER_SIZE_FOR_LABELS = 20;
    /* To make the text areas resizable but smaller than the frame, a constant
     * ratio is needed.
     */
    private static final int HEIGHT_RATIO = 10;
    private static final int WIDTH_RATIO = 6;

    private static final int LABEL_WIDTH = 100;
    private static final int LABEL_HEIGHT = 60;
    
    private final UserRegistrationController controller;
    private final JTextField attackerNameTextField;
    private final JTextField defenderNameTextField;
    private int verticalTextAreaSize;
    private int horizontalTextAreaSize;

    /*
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
        
        this.setDimensions(this.controller.getViewWidth() / HEIGHT_RATIO,
            this.controller.getViewHeight() / WIDTH_RATIO);

        final JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.PAGE_AXIS));
        final JButton goBackButton = new JButton(UserRegistrationScene.GO_BACK);
        final JButton submitButton = new JButton(UserRegistrationScene.SUBMIT);
        submitButton.setFont(Scene.FONT_MANAGER.getButtonFont());
        goBackButton.setFont(Scene.FONT_MANAGER.getButtonFont());
        goBackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        southPanel.add(submitButton);
        southPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        southPanel.add(goBackButton);
        southPanel.add(Box.createRigidArea(new Dimension(0, UserRegistrationScene.SPACE)));
        //this.testDoNotUse(southPanel); //TODO: delete;
        southPanel.setVisible(true);

        goBackButton.addActionListener((e) -> {
            this.clearTextAreas();
            this.controller.goToPreviousScene();
        });

        this.attachSubmitListener(submitButton);

        southPanel.setBackground(Scene.TRANSPARENT);
        elementsPanel.setBackground(Scene.TRANSPARENT);

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
            public void actionPerformed(ActionEvent arg0) {
                if (areUsernamesValid()) {
                    controller.registerMatchResult(attackerNameTextField.getText(),
                    defenderNameTextField.getText());
                    controller.goToNextScene();
                } else {
                    //JDialog con messaggio d'errore. TODO
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
        if (this.attackerNameTextField.getText().length() <= 0
            || this.defenderNameTextField.getText().length() <= 0) {
                return false;
            }
        return true;
    }

    /* Builds the area in which the user can insert player names. */
    private void constructInputLabels(final JPanel scene) {
        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
        inputPanel.setBackground(Scene.TRANSPARENT);

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

    private void prepareTextArea(JTextField text) {
        text.setColumns(50);
        text.setPreferredSize(new Dimension(this.horizontalTextAreaSize, this.verticalTextAreaSize));
        text.setFont(Scene.FONT_MANAGER.getButtonFont());
        text.setHorizontalAlignment(SwingConstants.CENTER);
        text.setDocument(new Limiter(CHARACTER_LIMIT));
    }

    /**
     * Resizes the text areas
     * @param x horizontal size
     * @param y vertical size
     */
    public void setDimensions(int x, int y) {
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

    /* This method only simulates the end of the match and communicates the results
     * to the controller; this entire method and the JButton related to it MUST BE REMOVED
     * before considering this class complete.
     * TODO: see above
     */
    /* private void testDoNotUse(final JPanel panel) {
        final JButton jb = new JButton("Add fake result");
        jb.addActionListener(new ActionListener() {
            private Random rand = new Random();
            @Override
            public void actionPerformed(ActionEvent arg0) {
                MatchResult attackerResult;
                MatchResult defenderResult;
                MatchResult[] possibleResults = MatchResult.values();
                attackerResult = possibleResults[rand.nextInt(3)];
                defenderResult = possibleResults[rand.nextInt(3)];
                controller.setEndMatchResults(attackerResult, defenderResult);
            }
            
        });
        jb.setAlignmentX(SwingConstants.CENTER);
        jb.setFont(Scene.FONT_MANAGER.getButtonFont());
        panel.add(jb);
    } */

    private void createLabel(JPanel panel, String labelContent) {
        final JLabel label = new JLabel();
        label.setText(labelContent);
        label.setFont(Scene.FONT_MANAGER.getModifiedFont(UserRegistrationScene.CHARACTER_SIZE_FOR_LABELS, Font.ITALIC));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.paintPanel(panel, UserRegistrationScene.LABEL_WIDTH, UserRegistrationScene.LABEL_HEIGHT, label);
    }

    private void paintPanel(JPanel mainPanel, int width, int height, JLabel label) {
        final JPanel paintedPanel = new JPanel() {
            @Override
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final URL imgURL = ClassLoader.getSystemResource(ROOT + SEP + "images" + SEP + IMAGE_NAME);
                final Image image = Toolkit.getDefaultToolkit().getImage(imgURL);
                customResize(image, width, height);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        paintedPanel.add(label);
        mainPanel.add(paintedPanel);
    }

    private void customResize(Image image, int width, int height) {
        image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
