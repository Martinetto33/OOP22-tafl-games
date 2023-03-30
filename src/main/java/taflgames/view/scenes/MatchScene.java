package taflgames.view.scenes;

import java.util.Optional;

import javax.swing.JPanel;

import taflgames.view.scenecontrollers.MatchSceneController;
/**
     * Andre: non so se la devo fare io o no. SI PUO' TRANQUILLAMENTE ELIMINARE
     */
public class MatchScene extends AbstractScene {
    private static final String MATCH = "BATTLE!";
    private static final int NUMB_CELLS_SIDE = 11;
    //private MatchController = new ImplMatchController();
    //TO DO: MODIFY BASICSCENECONTROLLER AS ABOVE
    private MatchSceneController controller;
    public MatchScene(final MatchSceneController controller) {
        super(MatchScene.MATCH, Optional.of("home-background.jpeg"));
        this.controller = controller;
        final JPanel scene = super.getScene();
        //final MatchPanelImpl match = new MatchPanelImpl(MatchScene.NUMB_CELLS_SIDE, 75);//CHANGE
        //scene.add(match);
    }
}
