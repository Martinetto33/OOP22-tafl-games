package taflgames.view.scenes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.JPanel;

import taflgames.common.Player;
import taflgames.common.code.Position;
import taflgames.view.scenecontrollers.MatchSceneController;
/**
     * Andre: non so se la devo fare io o no. SI PUO' TRANQUILLAMENTE ELIMINARE
     */
public class MatchScene extends AbstractScene {
    private static final String MATCH = "BATTLE!";
    private static final int NUMB_CELLS_SIDE = 11;
    //private MatchController = new ImplMatchController();
    //TO DO: MODIFY BASICSCENECONTROLLER AS ABOVE
    private final MatchSceneController controller;
    
    public MatchScene(final MatchSceneController controller) {
        super(MatchScene.MATCH, Optional.of("home-background.jpeg"));
        this.controller = controller;
        final JPanel scene = super.getScene();
        //final MatchPanelImpl match = new MatchPanelImpl(MatchScene.NUMB_CELLS_SIDE, 75);//CHANGE
        //scene.add(match);

        // When the match scene is set, cells and pieces must be drawn for the first time.
        this.update();
    }

    @Override
    public void update() {
        /*
         * The board shown in the match scene must reflect the current state of the model.
         * All cells and pieces must be drawn in the correct position and using the correct
         * sprite according to their type.
         */
        final Map<Position, List<String>> cellsMapping = this.controller.getCellsMapping();
        final Map<Player, Map<Position, String>> piecesMapping = this.controller.getPiecesMapping();
        /*
         * These two collections will be used to redraw cells and pieces in the MatchPanel.
         * The Strings in these two maps represent the type of cells and pieces at each position.
         * I suppose that the redrawing will follow these steps.
         * - The Strings will be used somehow to create CellImageInfo and PieceImageInfo objects.
         * - The methods drawBackgroundCells(), drawAllSpecialCells and drawAllPieces() of the MatchPanel will be called,
         *   passing the maps of positions and CellImageInfo/PieceImageInfo.
         */
    }
}
