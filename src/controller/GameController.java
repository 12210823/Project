package controller;


import listener.GameListener;
import model.*;
import view.*;
import view.UI.ImagePanel;
import view.UI.RoundButton;

import javax.swing.*;
import java.awt.*;
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
public class GameController implements GameListener,Serializable {

    public int type;
    private Chessboard model;
    public ChessboardComponent view;
    private PlayerColor currentPlayer;
    public PlayerColor winner;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    public int turn=1;
    public List<Steps> steps= new ArrayList<>();
    public List<Steps> possibleSteps= new ArrayList<>();
    public List<Steps> possibleForwards= new ArrayList<>();
    public List<Steps> possibleCaptures=new ArrayList<>();
    public void loadGameFromFile(String path)
    {
        int dot=0;
        for (int i=0;i<path.length();i++)
        {
            if (path.charAt(i)=='.')
            {
                dot++;
            }
        }
        if (dot==0||!path.substring(path.lastIndexOf(".")).equals(".txt"))
        {
            JDialog warning = new JDialog(this.view.getChessGameFrame(),"游戏结束",true);
            warning.setSize(350,200);
            JPanel panel = new ImagePanel("resource/Backgrounds/warning.png");
            panel.setLayout(new GridLayout(2, 1, 20, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            JLabel w1 = new JLabel("检测到非法修改文档！",SwingConstants.CENTER);
            JLabel w2 = new JLabel("已重新开始。",SwingConstants.CENTER);
            w1.setForeground(Color.white);
            w2.setForeground(Color.white);
            w1.setFont(new Font("微软雅黑",Font.PLAIN,30));
            w2.setFont(new Font("微软雅黑",Font.PLAIN,30));
            panel.add(w1);
            panel.add(w2);

            warning.add(panel);
            warning.setVisible(true);
            Restart();
        }
        else
        {
            ObjectInputStream objectInputStream;
            try {
                FileInputStream inputStream = new FileInputStream(path);
                objectInputStream=new ObjectInputStream(inputStream);
                steps=(List<Steps>) objectInputStream.readObject();
                List<Steps> steps = new ArrayList<>(this.steps);
                Restart();
                if (model.playBack(steps))
                {
                    view.playBack(steps);
                    view.repaint();
                    this.steps=steps;
                    turn=steps.size()+1;
                    int round = (turn + 1) / 2;
                    if(turn % 2 == 1)
                    {
                        view.getChessGameFrame().statusLabel.setText("第" + round + "回合，左方行棋");
                    }else {
                        view.getChessGameFrame().statusLabel.setText("第" + round + "回合，右方行棋");
                        currentPlayer = PlayerColor.RED;
                    }
                }
                else
                {
                    Restart();
                }
            } catch (ClassNotFoundException | IOException e)
            {
                JDialog warning = new JDialog(this.view.getChessGameFrame(),"游戏结束",true);
                warning.setSize(350,200);
                JPanel panel = new ImagePanel("resource/Backgrounds/warning.png");
                panel.setLayout(new GridLayout(2, 1, 20, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
                JLabel w1 = new JLabel("检测到非法修改文档！",SwingConstants.CENTER);
                JLabel w2 = new JLabel("已重新开始。",SwingConstants.CENTER);
                w1.setForeground(Color.white);
                w2.setForeground(Color.white);
                w1.setFont(new Font("微软雅黑",Font.PLAIN,30));
                w2.setFont(new Font("微软雅黑",Font.PLAIN,30));
                panel.add(w1);
                panel.add(w2);

                warning.add(panel);
                warning.setVisible(true);

                Restart();
                e.printStackTrace();
            }
        }
    }
    public void saveGameToFile(String path)
    {
        ObjectOutputStream objectOutputStream;
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
        view.getChessGameFrame().statusLabel.setText("第" + turn + "回合，左方行棋");
        if (steps.size() > 0) {
            steps.subList(0, steps.size()).clear();
        }
    }

    public void Replay()
    {
        if (type==0)
        {
            List<Steps> steps = new ArrayList<>(this.steps);
            System.out.println(steps.size());
            Restart();
            //view.repaint();
            if (steps.size()>0) {
                for (int i = 0; i < steps.size() - 1; i++) {
                    model.playBack(steps.get(i));
                    view.playBack(steps.get(i));
                    swapColor();
                    view.repaint();
                }
                steps.remove(steps.size()-1);
                this.steps = steps;
                turn = steps.size();
            }
        }
        else
        {
            List<Steps> steps = new ArrayList<>(this.steps);
            System.out.println(steps.size());
            Restart();
            //view.repaint();
            if (steps.size()>=2) {
                for (int i = 0; i < steps.size() - 2; i++) {
                    model.playBack(steps.get(i));
                    view.playBack(steps.get(i));
                    swapColor();
                    view.repaint();
                }
                steps.remove(steps.size()-1);
                steps.remove(steps.size()-1);
                this.steps = steps;
                turn = steps.size();
            }
        }
    }

    public void Playback()
    {
        List<Steps> steps = new ArrayList<>(this.steps);
        System.out.println(steps.size());
        Restart();
        //view.repaint();
        view.paintImmediately(0,0,view.getWidth(),view.getHeight());
        if (steps.size()>0) {
            turn = 1;
            for (Steps step : steps) {
                int round = (turn + 1) / 2;
                if(turn % 2 == 1)
                {
                    view.getChessGameFrame().statusLabel.setText("第" + round + "回合，左方行棋");
                    view.getChessGameFrame().statusLabel.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                }else {
                    view.getChessGameFrame().statusLabel.setText("第" + round + "回合，右方行棋");
                    view.getChessGameFrame().statusLabel.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                    currentPlayer = PlayerColor.RED;
                }

                model.playBack(step);
                view.playBack(step);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                turn++;
            }
            this.steps = steps;
            turn = steps.size() + 1;
            int round = (turn + 1) / 2;
            if(turn % 2 == 1)
            {
                view.getChessGameFrame().statusLabel.setText("第" + round + "回合，左方行棋");
                currentPlayer = PlayerColor.BLUE;
            }else {
                view.getChessGameFrame().statusLabel.setText("第" + round + "回合，右方行棋");
                currentPlayer = PlayerColor.RED;
            }
        }
    }
    public GameController(ChessboardComponent view, Chessboard model, int type) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.type=type;

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
            winner=PlayerColor.BLUE;
            return true;
        }
        else if (b==0)
        {
            winner=PlayerColor.RED;
            return true;
        }
        return false;
        // TODO: Check the board if there is a winner
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            steps.add(new Steps(model.getChessPieceAt(selectedPoint),model.getChessPieceAt(point),selectedPoint,point,turn));
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
            view.paintImmediately(0,0,view.getWidth(),view.getHeight());
            // TODO: if the chess enter Dens or Traps and so on
        }
        if (win())
        {
            End();
        }
        else {
            Computer();
            if (win()) {
                End();
            }
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
                steps.add(new Steps(model.getChessPieceAt(selectedPoint),model.getChessPieceAt(point),selectedPoint,point,turn));
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
                view.paintImmediately(0,0,view.getWidth(),view.getHeight());
            }
        }
        if (win())
        {
            End();
        }
        else {
            Computer();
            if (win()) {
                End();
            }
        }
        // TODO: Implement capture function
    }

    public Chessboard getModel() {
        return model;
    }
    public List<Steps> PossibleStep()
    {
        if (possibleSteps.size() > 0) {
            possibleSteps.subList(0, possibleSteps.size()).clear();
        }
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i,j);
                if (model.getChessPieceAt(temp) != null){
                    if(model.getChessPieceOwner(temp).equals(currentPlayer)){
                        for (int x = 0; x < Constant.CHESSBOARD_ROW_SIZE.getNum(); x++) {
                            for (int y = 0; y < Constant.CHESSBOARD_COL_SIZE.getNum(); y++) {
                                ChessboardPoint temp1 = new ChessboardPoint(x,y);
                                if(model.isValidMove(temp, temp1)){
                                    possibleSteps.add(new Steps(model.getChessPieceAt(temp),model.getChessPieceAt(temp1),temp,temp1,turn));
                                }
                                else if(model.getChessPieceAt(temp1)!=null&&model.isValidCapture(temp,temp1)){
                                    possibleSteps.add(new Steps(model.getChessPieceAt(temp),model.getChessPieceAt(temp1),temp,temp1,turn));
                                }
                            }
                        }
                    }
                }
            }
        }
        return possibleSteps;
    }

    public List<Steps> PossibleForwards()
    {
        if (possibleForwards.size() > 0) {
            possibleForwards.subList(0, possibleForwards.size()).clear();
        }
        List<Steps> possiblesteps=PossibleStep();
        for (Steps step:possiblesteps)
        {
            if (model.calculateDistance(step.src,new ChessboardPoint(3,0))>=model.calculateDistance(step.dest,new ChessboardPoint(3,0)))
            {
                possibleForwards.add(step);
            }

        }
        if (possibleForwards.size()==0)
        {
            possibleForwards.addAll(possibleSteps);
            return possiblesteps;
        }
        else
        {
            return possibleForwards;
        }
    }
    public Steps BiggestCaptures()
    {
        if (possibleCaptures.size() > 0) {
            possibleCaptures.subList(0, possibleCaptures.size()).clear();
        }
        List<Steps> possiblesteps=PossibleStep();
        Steps biggestCapture=possiblesteps.get(0);
        for (Steps step:possiblesteps)
        {
            if (model.getChessPieceAt(step.dest)!=null)
            {
                possibleCaptures.add(step);
            }
        }
        for (Steps step:possibleCaptures)
        {
            if (step.destPiece!=null)
            {
                if (biggestCapture.destPiece==null)
                {
                    biggestCapture=step;
                }
                else if (step.destPiece.rank>biggestCapture.destPiece.rank)
                {
                    biggestCapture=step;
                }
            }
        }
        return biggestCapture;
    }
    public void Computer()
    {
        if (!win()&&type!=0)
        {
            if (type==1) {
                if (currentPlayer.equals(PlayerColor.RED)) {
                    int j = (int) (Math.random() * PossibleStep().size());
                    Steps step = possibleSteps.get(j);
                    selectedPoint = step.src;
                    steps.add(step);
                    view.getGridComponentAt(selectedPoint).setSelected(true);
                    view.paintImmediately(0,0,view.getWidth(),view.getHeight());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    view.getGridComponentAt(selectedPoint).setSelected(false);
                    view.paintImmediately(0,0,view.getWidth(),view.getHeight());
                    for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                        for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                            ChessboardPoint temp = new ChessboardPoint(i,k);
                            if (model.getChessPieceAt(temp) == null){
                                if(model.isValidMove(selectedPoint, temp)){
                                    view.getGridComponentAt(temp).setSelected(true);
                                }
                            } else {
                                if(model.isValidCapture(selectedPoint,temp)){
                                    view.getGridComponentAt(temp).setSelected(true);
                                }
                            }
                        }
                    }
                    view.paintImmediately(0,0,view.getWidth(),view.getHeight());

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                        for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                            ChessboardPoint temp = new ChessboardPoint(i,k);
                            view.getGridComponentAt(temp).setSelected(false);
                        }
                    }
                    model.playBack(step);
                    view.playBack(step);
                    selectedPoint = null;
                    swapColor();
                    view.paintImmediately(0,0,view.getWidth(),view.getHeight());

                }
            }
            if (type==2)
            {
                if (currentPlayer.equals(PlayerColor.RED)) {
                    int j = (int) (Math.random() * PossibleForwards().size());
                    System.out.println(j);
                    Steps step = possibleForwards.get(j);
                    steps.add(step);
                    System.out.println(step.toString());
                    selectedPoint = step.src;
                    view.getGridComponentAt(selectedPoint).setSelected(true);
                    view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    view.getGridComponentAt(selectedPoint).setSelected(false);
                    view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                    for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                        for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                            ChessboardPoint temp = new ChessboardPoint(i, k);
                            if (model.getChessPieceAt(temp) == null) {
                                if (model.isValidMove(selectedPoint, temp)) {
                                    view.getGridComponentAt(temp).setSelected(true);
                                }
                            } else {
                                if (model.isValidCapture(selectedPoint, temp)) {
                                    view.getGridComponentAt(temp).setSelected(true);
                                }
                            }
                        }
                    }
                    view.paintImmediately(0, 0, view.getWidth(), view.getHeight());

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                        for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                            ChessboardPoint temp = new ChessboardPoint(i, k);
                            view.getGridComponentAt(temp).setSelected(false);
                        }
                    }
                    model.playBack(step);
                    view.playBack(step);
                    selectedPoint = null;
                    swapColor();
                    view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                }
            }
            if (type==3)
            {
                if (currentPlayer.equals(PlayerColor.RED)) {
                    if (BiggestCaptures().destPiece==null)
                    {
                        int j = (int) (Math.random() * PossibleForwards().size());
                        System.out.println(j);
                        Steps step = possibleForwards.get(j);
                        steps.add(step);
                        System.out.println(step.toString());
                        selectedPoint = step.src;
                        view.getGridComponentAt(selectedPoint).setSelected(true);
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        view.getGridComponentAt(selectedPoint).setSelected(false);
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                            for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                                ChessboardPoint temp = new ChessboardPoint(i, k);
                                if (model.getChessPieceAt(temp) == null) {
                                    if (model.isValidMove(selectedPoint, temp)) {
                                        view.getGridComponentAt(temp).setSelected(true);
                                    }
                                } else {
                                    if (model.isValidCapture(selectedPoint, temp)) {
                                        view.getGridComponentAt(temp).setSelected(true);
                                    }
                                }
                            }
                        }
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                            for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                                ChessboardPoint temp = new ChessboardPoint(i, k);
                                view.getGridComponentAt(temp).setSelected(false);
                            }
                        }
                        model.playBack(step);
                        view.playBack(step);
                        selectedPoint = null;
                        swapColor();
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                        PossibleStep();
                    }
                    else
                    {
                        Steps step = BiggestCaptures();
                        steps.add(step);
                        System.out.println(step.toString());
                        selectedPoint = step.src;
                        view.getGridComponentAt(selectedPoint).setSelected(true);
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        view.getGridComponentAt(selectedPoint).setSelected(false);
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                            for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                                ChessboardPoint temp = new ChessboardPoint(i, k);
                                if (model.getChessPieceAt(temp) == null) {
                                    if (model.isValidMove(selectedPoint, temp)) {
                                        view.getGridComponentAt(temp).setSelected(true);
                                    }
                                } else {
                                    if (model.isValidCapture(selectedPoint, temp)) {
                                        view.getGridComponentAt(temp).setSelected(true);
                                    }
                                }
                            }
                        }
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());


                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                            for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                                ChessboardPoint temp = new ChessboardPoint(i, k);
                                view.getGridComponentAt(temp).setSelected(false);
                            }
                        }
                        model.playBack(step);
                        view.playBack(step);
                        selectedPoint = null;
                        swapColor();
                        view.paintImmediately(0, 0, view.getWidth(), view.getHeight());
                    }
                }
            }
        }
    }
    public void End(){
        JDialog end = new JDialog(this.view.getChessGameFrame(),"游戏结束",true);
        end.setSize(320,200);
        JPanel panel = new ImagePanel("resource/Backgrounds/fallBackground.gif");
        panel.setLayout(new GridLayout(3, 1, 20, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 70, 10, 70));

        JLabel label;
        if(type == 0){
            if(winner == PlayerColor.BLUE){
                label = new JLabel("左方胜利",JLabel.CENTER);
            }else label = new JLabel("右方胜利",JLabel.CENTER);
        }else{
            if(winner == PlayerColor.BLUE){
                label = new JLabel("你赢了",JLabel.CENTER);
            }else label = new JLabel("你输了",JLabel.CENTER);
        }
        label.setForeground(Color.white);
        label.setFont(new Font("微软雅黑", Font.PLAIN, 40));

        JButton restart = new RoundButton("重新开始");
        JButton exit = new RoundButton("退出");
        restart.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        exit.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        restart.setSize(100,50);
        exit.setSize(100,50);

        restart.addActionListener(e -> {
            end.dispose();
            Restart();
        });
        exit.addActionListener(e -> {
            end.dispose();
            view.chessGameFrame.dispose();
            MainGameFrame mainGameFrame = new MainGameFrame(800, 500);
            mainGameFrame.setVisible(true);
        });

        panel.add(label);
        panel.add(restart);
        panel.add(exit);


        end.add(panel);
        end.setLocationRelativeTo(this.view.chessGameFrame);
        end.setVisible(true);
    }
}
