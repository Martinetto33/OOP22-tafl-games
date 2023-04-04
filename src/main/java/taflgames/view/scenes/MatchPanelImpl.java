package taflgames.view.scenes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import taflgames.common.code.Position;
import taflgames.view.loaderImages.LoaderImages;
import taflgames.view.loaderImages.LoaderImagesImpl;
import taflgames.view.scenecontrollers.MatchSceneController;

/**
 * implementation of MatchPanel.
 * MAY NEED IMPROOVEMENTS.
 */
public class MatchPanelImpl extends JPanel implements MatchPanel{

    private LoaderImages loader;
    /*
     * used to make sure the entire board will be visible entirely on the screen.
     * Without it it may be covered by the application-bar of the pc.
     */

    
    //private static final int HIGHT_OF_PC_APPLICATION_BAR = 100;
    private final Map<JButton, Position> mapButtons = new HashMap<>();
    private final Map<Position,JLabel> mapPieces = new HashMap<>();
    private final Map<Position,JLabel> mapSpecialCell = new HashMap<>();
    private final Map<Position,JLabel> mapBoard = new HashMap<>();
    private final Map<PieceImageInfo,ImageIcon> mapPieceImageIcons = new HashMap<>();
    private final Map<CellImageInfo,ImageIcon> mapCellsImageIcons = new HashMap<>();
    
    private final int mySize;
    private final int buttonPanelSize;
    private final int generalPanelSize;
    private final int piecePanelSize;
    private final int cellsPanelsSize;
    private final int sizeOfGrid;
    private Optional<Position> startingPosition = Optional.empty();
    private Optional<Position> destination = Optional.empty();
    private Set<Position> positionsToColor;
    private MatchSceneController controller;

    public MatchPanelImpl(final int numbCellsInGrid, final int sizeOfSide) {
        this.loader = new LoaderImagesImpl(sizeOfSide , 
                                            numbCellsInGrid);
        this.loader.loadCellsImages();
        this.loader.loadPiecesImages();
        mapPieceImageIcons.putAll(loader.getPieceImageMap());
        mapCellsImageIcons.putAll(loader.getCellImageMap());
        this.mySize = sizeOfSide ;
        this.setLayout(new FlowLayout());
        this.buttonPanelSize = this.mySize;
        this.generalPanelSize = this.mySize;
        this.piecePanelSize = this.mySize;
        this.cellsPanelsSize = this.mySize;
        this.sizeOfGrid = numbCellsInGrid;

        JPanel generPanel = new JPanel();
        generPanel.setLayout(new OverlayLayout(generPanel));
        generPanel.setSize(new Dimension(generalPanelSize, generalPanelSize));
        generPanel.setOpaque(false);
        this.add(generPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        buttonPanel.setSize(new Dimension(buttonPanelSize, buttonPanelSize));
        buttonPanel.setOpaque(false);
        generPanel.add(buttonPanel);

        JPanel piecePanel = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        piecePanel.setSize(new Dimension(piecePanelSize, piecePanelSize));
        piecePanel.setOpaque(false);
        generPanel.add(piecePanel);

        JPanel selectionPanel = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        selectionPanel.setSize(new Dimension(piecePanelSize, piecePanelSize));
        selectionPanel.setOpaque(false);
        generPanel.add(selectionPanel);

        JPanel specialCellsPanel = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        specialCellsPanel.setSize(new Dimension(cellsPanelsSize, cellsPanelsSize));
        specialCellsPanel.setOpaque(false);
        generPanel.add(specialCellsPanel);

        JPanel boardBackground = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        boardBackground.setSize(new Dimension(cellsPanelsSize, cellsPanelsSize));
        boardBackground.setBackground(Color.CYAN);
        generPanel.add(boardBackground);
        /*initializings panels*/
        this.createButtonsForGrid(buttonPanel, this.mapButtons, this.sizeOfGrid);
        this.createUnitsForGridLayerPanel(piecePanel, this.mapPieces, this.sizeOfGrid);
        this.createUnitsForGridLayerPanel(specialCellsPanel, this.mapSpecialCell, this.sizeOfGrid);
        this.createUnitsForGridLayerPanel(boardBackground, this.mapBoard, this.sizeOfGrid);
    }
    @Override
    public void drawAllPieces(Map<Position, PieceImageInfo> piecesAlive) {
        piecesAlive.forEach((a,b) -> {
            this.mapPieces.get(a).setIcon(null);
            this.mapPieces.get(a).setIcon(this.mapPieceImageIcons.get(b));
        });
    }
    @Override
    public void drawAllSpecialCells(Map<Position, CellImageInfo> cells) {
        cells.entrySet().stream()
                        .filter(elem -> elem.getValue().getName() != "CELL_BASIC"
                                            && elem.getValue().getName() != "CELL_EXIT"
                                            && elem.getValue().getName() != "CELL_THRONE")
                        .forEach(elem -> {
                            this.mapSpecialCell.get(elem.getKey()).setIcon(null);
                            this.mapSpecialCell.get(elem.getKey()).setIcon(this.mapCellsImageIcons.get(elem.getValue()));
                        });
    }
    @Override
    public void drawBackgroundCells(Map<Position, CellImageInfo> cells) {
        cells.entrySet().stream()
                        .filter(elem -> elem.getValue().getName() == "CELL_BASIC"
                                            || elem.getValue().getName() == "CELL_EXIT"
                                            || elem.getValue().getName() == "CELL_THRONE")
                        .forEach(elem -> {
                            this.mapSpecialCell.get(elem.getKey()).setIcon(null);
                            this.mapSpecialCell.get(elem.getKey()).setIcon(this.mapCellsImageIcons.get(elem.getValue()));
                        });
    }
    @Override
    public void removeAllIconsOnLayer(Map<Position, JLabel> mapLabel) {
        mapLabel.forEach((a,b) -> b.setIcon(null) );
    }
    /**
     * initializes a generic squared-gridlayered JPanel that contains a series of JLabels, 
     * which are then added to the map of JLabel.
     * @param me JPanel
     * @param myMapLabel map of JLabel
     * @param mySizeGrid number of cells on the side of gridlayered JPanel
     */
    private void createUnitsForGridLayerPanel(final JPanel me, final Map<Position,JLabel> myMapLabel, final int mySizeGrid) {
        if (me.getLayout().getClass() != new GridLayout().getClass()) {
            throw new IllegalArgumentException("i'm not a gridLayout");
        }
        if (mySizeGrid <= 0) {
            throw new IllegalArgumentException("size is <= 0");
        }
        Objects.requireNonNull(myMapLabel);
        for (int i=0; i < mySizeGrid; i++){
            for (int j=0; j < mySizeGrid; j++) {
                final JLabel labelPiece = new JLabel();
                labelPiece.setSize(this.mySize, this.mySize);
                labelPiece.setOpaque(false);
                labelPiece.setBackground(null);
                labelPiece.setIcon(null);
                myMapLabel.put(new Position(i, j), labelPiece);
                me.add(labelPiece);
            }
        }
    }
    @Override
    /**
     * WILL PROBABLY REMOVE
     */
    public void movePiece(Position originalPos, Position newPosition) {
        if (!originalPos.equals(newPosition) && mapPieces.get(newPosition).getIcon() != null) {
            throw new IllegalArgumentException("CRITICAL ERROR: there's another piece in the way! problem with MODEL");
        }
        if (!originalPos.equals(newPosition) && mapPieces.get(originalPos).getIcon() != null) {
            mapPieces.get(newPosition).setIcon(null);
            final Icon temp = mapPieces.get(originalPos).getIcon();
            mapPieces.get(originalPos).setIcon(null);
            mapPieces.get(newPosition).setIcon(temp);
        }
    }
    /**
     * 
     * @param me Jpannel of buttons.
     * @param myMapButtons map of Buttons.
     * @param mySizeGrid number of cells on the side of gridlayered JPanel.
     */
    private void createButtonsForGrid (final JPanel me, Map<JButton, Position> myMapButtons, final int mySizeGrid) {
        /**
         * TO DO: this listener must be changed after the creation of controller
         */
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = mapButtons.get(button);
                selectPosition(position);
                /* you clicked the same piece or a cell of its moveset 
                (coloured) */
                if ((startingPosition.isPresent() && destination.isPresent())
                    || startingPosition.isEmpty()) {
                    deselectHighlightedMoves();
                } else if (startingPosition.isPresent() && destination.isEmpty()) {
                    updateHighlightedMoves();
                }
            }
        };
        /**
         * creating the buttons.
         */
        for (int i=0; i < mySizeGrid; i++){
            for (int j=0; j < mySizeGrid; j++){
                final JButton jb = new JButton();
                jb.setOpaque(false);
                jb.setContentAreaFilled(false);
                jb.setIcon(null);
                jb.addActionListener(al);
                myMapButtons.put(jb, new Position(i, j));
                me.add(jb);
            }
        }
    }
    /**
     * MAY CHANGE IN THE FUTURE DEPENDING ON CONTROLLER IMPLEMENTATION.
     * this method sets the new set of position given by the controller
     * which rappresents the positions in which the currently 
     * selected piece can move.
     * 
     * @param positionsToColor 
     */
    private void setPositionToColor(final Set<Position> positionsToColor) {
        this.positionsToColor = positionsToColor;
    }
    /**
     * this method will colour the backgrounds of ONLY 
     * mapPieces's labels whose position is contained
     * in positionsToColour. The rest will have background null
     */
    private void updateHighlightedMoves() {
        /*instead of positionsToColor it will be used a method of controller
        * after this change setPositionToColor must be deleted.
        */
        mapPieces.forEach((x,y) -> {if(!x.equals(startingPosition.get())){
            y.setOpaque(false);
            y.setBackground(null);
        }});
        mapPieces.get(startingPosition.get()).setBackground(new Color(255, 155, 155));
        mapPieces.get(startingPosition.get()).setOpaque(true);
        if(destination.isPresent()) {
            mapPieces.get(destination.get()).setBackground(new Color(255, 155, 155));
            mapPieces.get(destination.get()).setOpaque(true);
            mapPieces.get(startingPosition.get()).setOpaque(false);
            mapPieces.get(startingPosition.get()).setBackground(null);
        } 
    }
    /**
     * unsets the background of all labels in mapPieces
     */
    private void deselectHighlightedMoves() {
        mapPieces.forEach((x,y) -> {
            y.setOpaque(false);
            y.setBackground(null);
        }); 
    }

    private void selectPosition(final Position pos) {
        if (this.startingPosition.isEmpty() && this.controller.isSourceSelectionValid(pos)) {
            // If the source position is empty and the selected one is a valid source,
            // then the selected position is set as source
            this.startingPosition = Optional.of(pos);
        } else if (this.startingPosition.get().equals(pos)) {
            // If the current source is equal to the selected position,
            // this means that the source is deselected
            this.startingPosition = Optional.empty();
        } else {
            // If the source is already set and it is not deselected,
            // then the selected position is the destination
            this.destination = Optional.of(pos);
            // Once the destination is selected, the move is triggered;
            // it will be performed if it is legal.
            this.requestMove();
        }
    }

    private void requestMove() {
        if (this.startingPosition.isPresent() && this.destination.isPresent()) {
            if (this.controller.moveIfLegal(this.startingPosition.get(), this.destination.get())) {
                // If the move is performed, startingPosition is reset
                this.startingPosition = Optional.empty();
            }
            // Destination is reset whether the move has been made or not
            this.destination = Optional.empty();
        }
    }

    @Override
    public Map<JButton, Position> getMapButtons() {
        return this.mapButtons;
    }
    @Override
    public Map<Position, JLabel> getMapPieces() {
        return this.mapPieces;
    }
    @Override
    public Map<Position, JLabel> getMapSpecialCell() {
        return this.mapSpecialCell;
    }
    @Override
    public Map<Position, JLabel> getMapBoard() {
        return this.mapBoard;
    }
    @Override
    public int getMySize() {
        return this.mySize;
    }

    @Override
    public Optional<Position> getStartingPosition() {
        return this.startingPosition;
    }
    @Override
    public Optional<Position> getDestination() {
        return this.destination;
    }
    @Override
    public void setMatchController(final MatchSceneController controller) {
        this.controller = controller;
    }
}
