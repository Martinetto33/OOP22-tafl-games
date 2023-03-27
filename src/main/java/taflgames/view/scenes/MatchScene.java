package taflgames.view.scenes;

import java.util.Optional;

import javax.swing.JPanel;

import taflgames.view.fontManager.FontManager;
import taflgames.view.scenecontrollers.BasicSceneController;

public class MatchScene extends AbstractScene{
    private static final String MATCH = "BATTLE!";
    private static final int NUMB_CELLS_SIDE = 11;
    //private MatchController = new ImplMatchController();
    //TO DO: MODIFY BASICSCENECONTROLLER AS ABOVE
    private BasicSceneController controller;
    public MatchScene(final BasicSceneController controller) {
        super(MatchScene.MATCH, Optional.of("home-background.jpeg"));
        this.controller = controller;
        final FontManager runeFont = new FontManager();
        final JPanel scene = super.getScene();
        final MatchPanelImpl match = new MatchPanelImpl(MatchScene.NUMB_CELLS_SIDE);
        scene.add(match);
    }
}
