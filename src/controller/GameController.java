package controller;


import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;
import view.Win;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
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
    public int turn = 1;
    public List<Steps> steps;

    public void loadGameFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(view.getChessGameFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
                List<Steps> stepList = (List<Steps>) inputStream.readObject();
                Restart();
                for (Steps step : stepList) {
                    model.runStep(step);
                    view.runStep(step);
                    view.repaint();
                    try {
                        Thread.sleep(250);
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /*for (Steps step : stepList) {
                    model.runStep(step);
                }
                view.playBack(stepList);
                view.repaint();*/
                //这样也行，不过是直接复原，学长的那个是每过一段时间走一步
                this.steps = stepList;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void saveGameToFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(view.getChessGameFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
                outputStream.writeObject(steps);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public void Replay()
    {
        Restart();
        model.playBack(steps);
        view.playBack(steps);
        view.repaint();
    }
    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        steps = new LinkedList<>();

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
        int round = (turn + 1) / 2;
        if(turn % 2 == 1){
            view.getChessGameFrame().statusLabel.setText("第" + round + "回合，左方行棋");
        }else view.getChessGameFrame().statusLabel.setText("第" + round + "回合，右方行棋");
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
            Steps step = model.recordStep(selectedPoint,point,currentPlayer,turn);
            steps.add(step);
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
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent chess) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                    for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                        ChessboardPoint temp = new ChessboardPoint(i,j);
                        if (model.getChessPieceAt(temp) == null){
                            if(model.isValidMove(selectedPoint, temp)){
                                view.getGridComponentAt(temp).setSelected(true);
                                view.getGridComponentAt(temp).repaint();
                            }
                        } else {
                            if(model.isValidCapture(selectedPoint,temp)){
                                view.getGridComponentAt(temp).setSelected(true);
                                view.getGridComponentAt(temp).repaint();
                            }
                        }
                    }
                }
                chess.setSelected(true);
                chess.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                    ChessboardPoint temp = new ChessboardPoint(i,j);
                    view.getGridComponentAt(temp).setSelected(false);
                    view.getGridComponentAt(temp).repaint();
                }
            }
            chess.setSelected(false);
            chess.repaint();
        }
        if (selectedPoint != null) {
            if (model.isValidCapture(selectedPoint, point)) {
                Steps step = model.recordStep(selectedPoint,point,currentPlayer,turn);
                steps.add(step);
                model.captureChessPiece(selectedPoint, point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
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
