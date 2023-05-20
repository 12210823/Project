package view;


import controller.GameController;
import model.*;
import view.AnimalChessComponent.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> trapCell = new HashSet<>();
    private final Set<ChessboardPoint> densCell = new HashSet<>();
    private GameController gameController;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 9;
        int height = CHESS_SIZE * 7;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    switch (chessPiece.getRank()) {
                        case 8 : gridComponents[i][j].add(new ElephantChessComponent(chessPiece.getOwner(), CHESS_SIZE));break;
                        case 7 : gridComponents[i][j].add(new LionChessComponent(chessPiece.getOwner(), CHESS_SIZE));break;
                        case 6 : gridComponents[i][j].add(new TigerChessComponent(chessPiece.getOwner(), CHESS_SIZE));break;
                        case 5 : gridComponents[i][j].add(new LeopardChessComponent(chessPiece.getOwner(), CHESS_SIZE));break;
                        case 4 : gridComponents[i][j].add(new WolfChessComponent(chessPiece.getOwner(), CHESS_SIZE));break;
                        case 3 : gridComponents[i][j].add(new DogChessComponent(chessPiece.getOwner(), CHESS_SIZE));break;
                        case 2 : gridComponents[i][j].add(new CatChessComponent(chessPiece.getOwner(), CHESS_SIZE));break;
                        case 1 : gridComponents[i][j].add(new RatChessComponent(chessPiece.getOwner(), CHESS_SIZE));break;
                    }
                }
            }
        }

    }

    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(1,3));
        riverCell.add(new ChessboardPoint(2,3));
        riverCell.add(new ChessboardPoint(1,4));
        riverCell.add(new ChessboardPoint(2,4));
        riverCell.add(new ChessboardPoint(1,5));
        riverCell.add(new ChessboardPoint(2,5));

        riverCell.add(new ChessboardPoint(4,3));
        riverCell.add(new ChessboardPoint(5,3));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,5));

        trapCell.add(new ChessboardPoint(2,0));
        trapCell.add(new ChessboardPoint(4,0));
        trapCell.add(new ChessboardPoint(3,1));
        trapCell.add(new ChessboardPoint(3,7));
        trapCell.add(new ChessboardPoint(2,8));
        trapCell.add(new ChessboardPoint(4,8));

        densCell.add(new ChessboardPoint(3,0));
        densCell.add(new ChessboardPoint(3,8));

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(GridType.RIVER, calculatePoint(i, j), CHESS_SIZE);
                } else if (trapCell.contains(temp)) {
                    cell = new CellComponent(GridType.TRAP, calculatePoint(i, j), CHESS_SIZE);
                } else if (densCell.contains(temp)) {
                    cell = new CellComponent(GridType.DENS, calculatePoint(i, j), CHESS_SIZE);
                } else {
                    cell = new CellComponent(GridType.LAND, calculatePoint(i, j), CHESS_SIZE);
                }
                this.add(cell);
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }
}
