package taflgames.view.scenes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
import taflgames.view.imagesloader.LoaderImages;
import taflgames.view.imagesloader.LoaderImagesImpl;
import taflgames.view.scenecontrollers.MatchSceneController;

/**
 * Implementation of MatchPanel.
 */
public class MatchPanelImpl extends JPanel implements MatchPanel {

    public static final long serialVersionUID = 1L;

    private final Map<JButton, Position> mapButtons = new HashMap<>();
    private final Map<Position, JLabel> mapPieces = new HashMap<>();
    private final Map<Position, JLabel> mapSpecialCell = new HashMap<>();
    private final Map<Position, JLabel> mapBoard = new HashMap<>();
    private final Map<PieceImageInfo, ImageIcon> mapPieceImageIcons = new HashMap<>();
    private final Map<CellImageInfo, ImageIcon> mapCellsImageIcons = new HashMap<>();

    private final int mySize;
    private Optional<Position> startingPosition = Optional.empty();
    private Optional<Position> destination = Optional.empty();
    private MatchSceneController controller;

    /**
     * Creates the panel that shows the board.
     * @param numbCellsInGrid the number of cells of the grid
     * @param sizeOfSide the dimension of the grid (expressed in number of cells on one side)
     */
    public MatchPanelImpl(final int numbCellsInGrid, final int sizeOfSide) {
        final LoaderImages loader = new LoaderImagesImpl(sizeOfSide, numbCellsInGrid);
        loader.loadCellsImages();
        loader.loadPiecesImages();
        mapPieceImageIcons.putAll(loader.getPieceImageMap());
        mapCellsImageIcons.putAll(loader.getCellImageMap());
        this.mySize = sizeOfSide;
        this.setLayout(new FlowLayout());
        final int buttonPanelSize = this.mySize;
        final int generalPanelSize = this.mySize;
        final int piecePanelSize = this.mySize;
        final int cellsPanelsSize = this.mySize;
        final int sizeOfGrid = numbCellsInGrid;

        final JPanel generPanel = new JPanel();
        generPanel.setLayout(new OverlayLayout(generPanel));
        generPanel.setSize(new Dimension(generalPanelSize, generalPanelSize));
        generPanel.setOpaque(false);
        this.add(generPanel);

        final JPanel buttonPanel = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        buttonPanel.setSize(new Dimension(buttonPanelSize, buttonPanelSize));
        buttonPanel.setOpaque(false);
        generPanel.add(buttonPanel);

        final JPanel piecePanel = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        piecePanel.setSize(new Dimension(piecePanelSize, piecePanelSize));
        piecePanel.setOpaque(false);
        generPanel.add(piecePanel);

        final JPanel selectionPanel = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        selectionPanel.setSize(new Dimension(piecePanelSize, piecePanelSize));
        selectionPanel.setOpaque(false);
        generPanel.add(selectionPanel);

        final JPanel specialCellsPanel = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        specialCellsPanel.setSize(new Dimension(cellsPanelsSize, cellsPanelsSize));
        specialCellsPanel.setOpaque(false);
        generPanel.add(specialCellsPanel);

        final JPanel boardBackground = new JPanel(new GridLayout(sizeOfGrid, sizeOfGrid));
        boardBackground.setSize(new Dimension(cellsPanelsSize, cellsPanelsSize));
        boardBackground.setBackground(Color.CYAN);
        generPanel.add(boardBackground);
        /*initializings panels*/
        this.createButtonsForGrid(buttonPanel, this.mapButtons, sizeOfGrid);
        this.createUnitsForGridLayerPanel(piecePanel, this.mapPieces, sizeOfGrid);
        this.createUnitsForGridLayerPanel(specialCellsPanel, this.mapSpecialCell, sizeOfGrid);
        this.createUnitsForGridLayerPanel(boardBackground, this.mapBoard, sizeOfGrid);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawAllPieces(final Map<Position, PieceImageInfo> piecesAlive) {
        piecesAlive.forEach((a, b) -> {
            this.mapPieces.get(a).setIcon(null);
            this.mapPieces.get(a).setIcon(this.mapPieceImageIcons.get(b));
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawAllSpecialCells(final Map<Position, CellImageInfo> cells) {
        cells.entrySet().stream()
                        .filter(elem -> !("CELL_BASIC".equals(elem.getValue().getName()))
                                            && !("CELL_EXIT".equals(elem.getValue().getName()))
                                            && !("CELL_THRONE".equals(elem.getValue().getName())))
                        .forEach(elem -> {
                            this.mapSpecialCell.get(elem.getKey()).setIcon(null);
                            this.mapSpecialCell.get(elem.getKey()).setIcon(this.mapCellsImageIcons.get(elem.getValue()));
                        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawBackgroundCells(final Map<Position, CellImageInfo> cells) {
        cells.entrySet().stream()
                        .filter(elem -> "CELL_BASIC".equals(elem.getValue().getName())
                                            || "CELL_EXIT".equals(elem.getValue().getName())
                                            || "CELL_THRONE".equals(elem.getValue().getName()))
                        .forEach(elem -> {
                            this.mapSpecialCell.get(elem.getKey()).setIcon(null);
                            this.mapSpecialCell.get(elem.getKey()).setIcon(this.mapCellsImageIcons.get(elem.getValue()));
                        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAllIconsOnLayer(final Map<Position, JLabel> mapLabel) {
        mapLabel.forEach((a, b) -> b.setIcon(null));
    }

    /**
     * initializes a generic squared-gridlayered JPanel that contains a series of JLabels, 
     * which are then added to the map of JLabel.
     * @param me JPanel
     * @param myMapLabel map of JLabel
     * @param mySizeGrid number of cells on the side of gridlayered JPanel
     */
    private void createUnitsForGridLayerPanel(final JPanel me, final Map<Position, JLabel> myMapLabel, final int mySizeGrid) {
        if (me.getLayout().getClass() != GridLayout.class) {
            throw new IllegalArgumentException("i'm not a gridLayout");
        }
        if (mySizeGrid <= 0) {
            throw new IllegalArgumentException("size is <= 0");
        }
        Objects.requireNonNull(myMapLabel);
        for (int i = 0; i < mySizeGrid; i++) {
            for (int j = 0; j < mySizeGrid; j++) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void movePiece(final Position originalPos, final Position newPosition) {
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
     * @param me Jpannel of buttons.
     * @param myMapButtons map of Buttons.
     * @param mySizeGrid number of cells on the side of gridlayered JPanel.
     */
    private void createButtonsForGrid(final JPanel me, final Map<JButton, Position> myMapButtons, final int mySizeGrid) {
        final ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final var button = (JButton) e.getSource();
                final var position = mapButtons.get(button);
                selectPosition(position);
                /* you clicked the same piece or a cell of its moveset 
                    (coloured) */
                if (startingPosition.isPresent() && destination.isPresent()
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
        for (int i = 0; i < mySizeGrid; i++) {
            for (int j = 0; j < mySizeGrid; j++) {
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
     * This method will colour the backgrounds of selected positions.
     */
    private void updateHighlightedMoves() {
        final Color highlightColor = new Color(255, 155, 155);
        mapPieces.forEach((x, y) -> {
            if (!x.equals(startingPosition.get())) {
                y.setOpaque(false);
                y.setBackground(null);
            }
        });
        mapPieces.get(startingPosition.get()).setBackground(highlightColor);
        mapPieces.get(startingPosition.get()).setOpaque(true);
        if (destination.isPresent()) {
            mapPieces.get(destination.get()).setBackground(highlightColor);
            mapPieces.get(destination.get()).setOpaque(true);
            mapPieces.get(startingPosition.get()).setOpaque(false);
            mapPieces.get(startingPosition.get()).setBackground(null);
        }
    }

    /**
     * Unsets the background of all labels in mapPieces.
     */
    private void deselectHighlightedMoves() {
        mapPieces.forEach((x, y) -> {
            y.setOpaque(false);
            y.setBackground(null);
        }); 
    }

    private void selectPosition(final Position pos) {
        if (this.startingPosition.isEmpty() && this.controller.isSourceSelectionValid(pos)) {
            // If the source position is empty and the selected one is a valid source,
            // then the selected position is set as source
            this.startingPosition = Optional.of(pos);
        } else if (this.startingPosition.isPresent() && this.startingPosition.get().equals(pos)) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<JButton, Position> getMapButtons() {
        return new HashMap<>(this.mapButtons);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, JLabel> getMapPieces() {
        return new HashMap<>(this.mapPieces);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, JLabel> getMapSpecialCell() {
        return new HashMap<>(this.mapSpecialCell);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, JLabel> getMapBoard() {
        return new HashMap<>(this.mapBoard);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMySize() {
        return this.mySize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Position> getStartingPosition() {
        return this.startingPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Position> getDestination() {
        return this.destination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMatchController(final MatchSceneController controller) {
        this.controller = controller;
    }

}
