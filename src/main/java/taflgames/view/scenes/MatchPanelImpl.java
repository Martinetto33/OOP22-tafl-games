package taflgames.view.scenes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

public class MatchPanelImpl extends JPanel implements MatchPanel{

    private LoaderImages loader;

    private final Map<JButton, Position> mapBottoni = new HashMap<>();
    private final Map<Position,JLabel> mapPedine = new HashMap<>();
    private final Map<Position,JLabel> mapSpecialCell = new HashMap<>();
    private final Map<Position,JLabel> mapBoard = new HashMap<>();
    private final Map<Position,JLabel> mapSelection = new HashMap<>();
    private final Map<PieceImageInfo,ImageIcon> mapPieceImageIcons = new HashMap<>();
    private final Map<CellImageInfo,ImageIcon> mapCellsImageIcons = new HashMap<>();
    
    private final int mySize;
    private final int buttonPanelSize;
    private final int generalPanelSize;
    private final int piecePanelSize;
    private final int cellsPanelsSize;
    private final int sizeOfGrid;
    private Position precPos;

    public MatchPanelImpl(final int numbCellsInGrid, final int sizeOfSide) {
        this.loader = new LoaderImagesImpl(sizeOfSide, numbCellsInGrid);
        this.loader.loadCellsImages();
        this.loader.loadPiecesImages();
        mapPieceImageIcons.putAll(loader.getPieceImageMap());
        mapCellsImageIcons.putAll(loader.getCellImageMap());
        this.mySize = sizeOfSide;
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

        this.createButtonsForGrid(buttonPanel, this.mapBottoni, this.sizeOfGrid); //ok
        this.createUnitsForGridLayerPanel(piecePanel, this.mapPedine, this.sizeOfGrid); //ok
        this.createUnitsForGridLayerPanel(specialCellsPanel, this.mapSpecialCell, this.sizeOfGrid); //ok
        this.createUnitsForGridLayerPanel(boardBackground, this.mapBoard, this.sizeOfGrid);//ok
        this.createUnitsForGridLayerPanel(selectionPanel, this.mapSelection, this.sizeOfGrid);//

    }

    @Override
    public void drawAllPieces(Map<Position, PieceImageInfo> piecesAlive) {
        piecesAlive.forEach((a,b) -> {
            this.mapPedine.get(a).setIcon(null);
            this.mapPedine.get(a).setIcon(this.mapPieceImageIcons.get(b));
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
        /* ImageIcon imageTombAttacker = mapCellsImageIcons.entrySet().stream()
                                    .filter(elem -> elem.getKey().getName().equals("CELL_TOMB"))
                                    .filter(null)
                                    .map(elem -> elem.getValue())
                                    .findAny()
                                    .get();
        cells.entrySet().stream().filter(cell -> cell.getValue().getName().equals("CELL_TOMB_ATTACKER"))
                                .forEach(cell -> {
                                    this.mapBoard.get(cell.getKey()).setIcon(null);
                                    this.mapBoard.get(cell.getKey()).setIcon(imageTombAttacker);
                                });
        ImageIcon imageTombDefender = mapCellsImageIcons.entrySet().stream()
                                .filter(elem -> elem.getKey().getName().equals("CELL_TOMB_DEFENDERS"))
                                .map(elem -> elem.getValue())
                                .findAny()
                                .get();
        cells.entrySet().stream().filter(cell -> cell.getValue().getName().equals("CELL_TOMB_DEFENDERS"))
                            .forEach(cell -> {
                                this.mapBoard.get(cell.getKey()).setIcon(null);
                                this.mapBoard.get(cell.getKey()).setIcon(imageTombDefender);
                            });

        ImageIcon imageSlider0 = mapCellsImageIcons.entrySet().stream()
                                                    .filter(elem -> elem.getKey().getName().equals("CELL_SLIDER_0"))
                                                    .map(elem -> elem.getValue()).findAny().get();
        cells.entrySet().stream().filter(cell -> cell.getValue().getName().equals("CELL_TOMB_DEFENDERS"))
                                .forEach(cell -> {
                                    this.mapBoard.get(cell.getKey()).setIcon(null);
                                    this.mapBoard.get(cell.getKey()).setIcon(imageSlider);
                                }); */
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
        /* ImageIcon imageBasicCell = mapCellsImageIcons.entrySet().stream()
                                    .filter(elem -> elem.getKey().getName().equals("CELL_BASIC"))
                                    .map(elem -> elem.getValue())
                                    .findAny()
                                    .get();
        cells.entrySet().stream().filter(cell -> cell.getValue().getName().equals("CELL_BASIC"))
                                .forEach(cell -> {
                                    this.mapBoard.get(cell.getKey()).setIcon(null);
                                    this.mapBoard.get(cell.getKey()).setIcon(imageBasicCell);
                                });
        ImageIcon imageThrone = mapCellsImageIcons.entrySet().stream()
                                    .filter(elem -> elem.getKey().getName().equals("CELL_THRONE"))
                                    .map(elem -> elem.getValue())
                                    .findAny()
                                    .get();
        cells.entrySet().stream().filter(cell -> cell.getValue().getName().equals("CELL_THRONE"))
                                .forEach(cell -> {
                                    this.mapBoard.get(cell.getKey()).setIcon(null);
                                    this.mapBoard.get(cell.getKey()).setIcon(imageThrone);
                                });
        ImageIcon imageExit= mapCellsImageIcons.entrySet().stream()
                                    .filter(elem -> elem.getKey().getName().equals("CELL_EXIT"))
                                    .map(elem -> elem.getValue())
                                    .findAny()
                                    .get();
        cells.entrySet().stream().filter(cell -> cell.getValue().getName().equals("CELL_EXIT"))
                                .forEach(cell -> {
                                    this.mapBoard.get(cell.getKey()).setIcon(null);
                                    this.mapBoard.get(cell.getKey()).setIcon(imageExit);
                                }); */
    }

    @Override
    public void removeAllIconsOnLayer(Map<Position, JLabel> mapLabel) {
        mapLabel.forEach((a,b) -> b.setIcon(null) );
    }
    
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
    public void movePiece(Position originalPos, Position newPosition) {
        if (!originalPos.equals(newPosition) && mapPedine.get(newPosition).getIcon() != null) {
            throw new IllegalArgumentException("CRITICAL ERROR: there's another piece in the way! problem with MODEL");
        }
        if (!originalPos.equals(newPosition) && mapPedine.get(originalPos).getIcon() != null) {
            mapPedine.get(newPosition).setIcon(null);
            final Icon temp = mapPedine.get(originalPos).getIcon();
            mapPedine.get(originalPos).setIcon(null);
            mapPedine.get(newPosition).setIcon(temp);
        }
    }
    public void createButtonsForGrid (final JPanel me, Map<JButton, Position> myMapButtons, final int mySizeGrid) {
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
        	    var button = (JButton)e.getSource();
        	    var position = mapBottoni.get(button);
                if(precPos != null) {
                    mapSelection.get(precPos).setOpaque(false);
                    mapSelection.get(precPos).setBackground(null);
                }
                try{
                    if (mapPedine.get(position).getIcon() != null) {
                        precPos = position;
                        mapSelection.get(position).setOpaque(true);
                        mapSelection.get(position).setBackground(new Color(255, 155, 155));
                    }
                } catch(NullPointerException n){
                    /*no action necessary */
                }
            }
        };

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

    public Map<JButton, Position> getMapBottoni() {
        return this.mapBottoni;
    }

    public Map<Position, JLabel> getMapPedine() {
        return this.mapPedine;
    }

    public Map<Position, JLabel> getMapSpecialCell() {
        return this.mapSpecialCell;
    }

    public Map<Position, JLabel> getMapBoard() {
        return this.mapBoard;
    }

    public int getMySize() {
        return this.mySize;
    }
    
}
