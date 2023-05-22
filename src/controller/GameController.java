package controller;


import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;
import view.Win;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;
    public PlayerColor winner;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    public int turn=1;
    List<Steps> steps= new ArrayList<>();

    public void loadGameFromFile(String path)
    {
        ObjectInputStream objectInputStream=null;
        try {
            FileInputStream inputStream = new FileInputStream(path);
            objectInputStream=new ObjectInputStream(inputStream);
            steps=(List<Steps>) objectInputStream.readObject();
            Restart();
            model.playBack(steps);
            view.playBack(steps);
            view.repaint();
        } catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }
    }
    public void saveGameToFile(String path)
    {
        ObjectOutputStream objectOutputStream=null;
        try {
            FileOutputStream outputStream = new FileOutputStream(path);
            objectOutputStream=new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(steps);
            objectOutputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Restart()
    {
        //view.registerController(this);
        model.initPieces();
        view.initiateGridComponents();
        view.initiateChessComponent(model);
        view.repaint();
        currentPlayer=PlayerColor.BLUE;
        winner=null;
        selectedPoint=null;
        turn=1;
        view.getChessGameFrame().statusLabel.setText("Turn: " + view.getChessGameFrame().chessboardComponent.getGameController().turn);
        if (steps.size() > 0) {
            steps.subList(0, steps.size()).clear();
        }
    }

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_ROW_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        turn++;
        view.getChessGameFrame().statusLabel.setText("Turn: " + view.getChessGameFrame().chessboardComponent.getGameController().turn);
    }

    private boolean win() {
        int b=0,r=0;
        for (int i=0;i<9;i++)
        {
            for (int j=0;j<7;j++)
            {
                if (model.grid[j][i].getPiece()!=null)
                {
                    if (model.grid[j][i].getPiece().getOwner() == PlayerColor.BLUE) {
                        b++;
                    }
                    if (model.grid[j][i].getPiece().getOwner() == PlayerColor.RED) {
                        r++;
                    }
                }
            }
        }
        if (model.grid[3][8].getPiece()!=null&&model.grid[3][8].getPiece().getOwner()==PlayerColor.BLUE)
        {
            winner=PlayerColor.BLUE;
            return true;
        }
        else if (model.grid[3][0].getPiece()!=null&&model.grid[3][0].getPiece().getOwner()==PlayerColor.RED)
        {
            winner=PlayerColor.RED;
            return true;
        }
        else if (r==0)
        {
            winner=PlayerColor.RED;
            return true;
        }
        else if (b==0)
        {
            winner=PlayerColor.BLUE;
            return true;
        }
        return false;
        // TODO: Check the board if there is a winner
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessboardPoint temp = new ChessboardPoint(i,j);
                    view.getGridComponentAt(temp).setSelected(false);
                }
            }
            swapColor();
            view.repaint();
            // TODO: if the chess enter Dens or Traps and so on
        }
        if (win())
        {
            Win gui = new Win(winner);
            gui.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            gui.setSize(250, 100);
            gui.setVisible(true);
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent chess, ArrayList<CellComponent> Valid) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                for (int i = 0; i < Valid.size(); i++) {
                    Valid.get(i).setSelected(true);
                    Valid.get(i).repaint();
                }
                chess.setSelected(true);
                chess.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            for (int i = 0; i < Valid.size(); i++) {
                Valid.get(i).setSelected(false);
                Valid.get(i).repaint();
            }
            chess.setSelected(false);
            chess.repaint();
        }
        if (selectedPoint != null) {
            if (model.isValidCapture(selectedPoint, point)) {
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                steps.add(new Steps(model.getChessPieceAt(selectedPoint),model.getChessPieceAt(point),selectedPoint,point,turn));
                selectedPoint = null;
                swapColor();
                for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                    for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                        ChessboardPoint temp = new ChessboardPoint(i,j);
                        view.getGridComponentAt(temp).setSelected(false);
                        view.getGridComponentAt(temp).repaint();
                    }
                }
                view.repaint();
            }
        }
        if (win())
        {
            Win gui = new Win(winner);
            gui.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            gui.setSize(250, 100);
            gui.setVisible(true);
        }
        // TODO: Implement capture function
    }

    public Chessboard getModel() {
        return model;
    }
}
