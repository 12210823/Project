package model;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard{
    public Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
    }

    public void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void initPieces() {
        grid[0][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Lion",7));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.BLUE, "Tiger",6));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Dog",3));
        grid[5][1].setPiece(new ChessPiece(PlayerColor.BLUE, "Cat",2));
        grid[0][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Rat",1));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Leopard",5));
        grid[4][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Wolf",4));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.BLUE, "Elephant",8));
        grid[6][8].setPiece(new ChessPiece(PlayerColor.RED, "Lion",7));
        grid[0][8].setPiece(new ChessPiece(PlayerColor.RED, "Tiger",6));
        grid[5][7].setPiece(new ChessPiece(PlayerColor.RED, "Dog",3));
        grid[1][7].setPiece(new ChessPiece(PlayerColor.RED, "Cat",2));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.RED, "Rat",1));
        grid[4][6].setPiece(new ChessPiece(PlayerColor.RED, "Leopard",5));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.RED, "Wolf",4));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.RED, "Elephant",8));
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    public int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        setChessPiece(dest,null);
        setChessPiece(dest,removeChessPiece(src));
        // TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        if (getChessPieceAt(src).getRank()!=7&&getChessPieceAt(src).getRank()!=6&&getChessPieceAt(src).getRank()!=1) {
            if ((dest.getRow()==3&&dest.getCol()==1)||(dest.getRow()==3&&dest.getCol()==2)||(dest.getRow()==4&&dest.getCol()==1)||
                    (dest.getRow()==4&&dest.getCol()==2)||(dest.getRow()==5&&dest.getCol()==1)||(dest.getRow()==5&&dest.getCol()==2)||
                    (dest.getRow()==3&&dest.getCol()==4)||(dest.getRow()==3&&dest.getCol()==5)||(dest.getRow()==4&&dest.getCol()==4)||
                    (dest.getRow()==4&&dest.getCol()==5)||(dest.getRow()==5&&dest.getCol()==4)||(dest.getRow()==5&&dest.getCol()==5))
            {
                return false;
            }
            else
            {
                return calculateDistance(src, dest) == 1;
            }
        }
        if (getChessPieceAt(src).getRank()==7||getChessPieceAt(src).getRank()==6)
        {
            if ((src.getRow()==2&&(src.getCol()==1||src.getCol()==2)&&dest.getRow()==6&&dest.getCol()==src.getCol()&&grid[3][src.col].getPiece()==null&&grid[4][src.col].getPiece()==null&&grid[5][src.col].getPiece()==null)||
                (src.getRow()==2&&(src.getCol()==4||src.getCol()==5)&&dest.getRow()==6&&dest.getCol()==src.getCol()&&grid[3][src.col].getPiece()==null&&grid[4][src.col].getPiece()==null&&grid[5][src.col].getPiece()==null)||
                (src.getRow()==6&&(src.getCol()==1||src.getCol()==2)&&dest.getRow()==2&&dest.getCol()==src.getCol()&&grid[3][src.col].getPiece()==null&&grid[4][src.col].getPiece()==null&&grid[5][src.col].getPiece()==null)||
                (src.getRow()==6&&(src.getCol()==4||src.getCol()==5)&&dest.getRow()==2&&dest.getCol()==src.getCol()&&grid[3][src.col].getPiece()==null&&grid[4][src.col].getPiece()==null&&grid[5][src.col].getPiece()==null)||
                        (src.getCol()==0&&(src.getRow()==3||src.getRow()==4||src.getRow()==5)&&dest.getCol()==3&&dest.getRow()==src.getRow()&&grid[src.row][1].getPiece()==null&&grid[src.row][2].getPiece()==null)||
                        (src.getCol()==3&&(src.getRow()==3||src.getRow()==4||src.getRow()==5)&&dest.getCol()==0&&dest.getRow()==src.getRow()&&grid[src.row][1].getPiece()==null&&grid[src.row][2].getPiece()==null)||
                        (src.getCol()==3&&(src.getRow()==3||src.getRow()==4||src.getRow()==5)&&dest.getCol()==6&&dest.getRow()==src.getRow()&&grid[src.row][4].getPiece()==null&&grid[src.row][5].getPiece()==null)||
                        (src.getCol()==6&&(src.getRow()==3||src.getRow()==4||src.getRow()==5)&&dest.getCol()==3&&dest.getRow()==src.getRow()&&grid[src.row][4].getPiece()==null&&grid[src.row][5].getPiece()==null))
            {
                return true;
            }
            else
            {
                return calculateDistance(src,dest)==1;
            }
        }
        else
        {
            return calculateDistance(src,dest)==1;
        }
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if ((dest.getRow()==3&&dest.getCol()==1)||(dest.getRow()==3&&dest.getCol()==2)||(dest.getRow()==4&&dest.getCol()==1)||
                (dest.getRow()==4&&dest.getCol()==2)||(dest.getRow()==5&&dest.getCol()==1)||(dest.getRow()==5&&dest.getCol()==2)||
                (dest.getRow()==3&&dest.getCol()==4)||(dest.getRow()==3&&dest.getCol()==5)||(dest.getRow()==4&&dest.getCol()==4)||
                (dest.getRow()==4&&dest.getCol()==5)||(dest.getRow()==5&&dest.getCol()==4)||(dest.getRow()==5&&dest.getCol()==5))
            {
                return false;
            }
        else if (getChessPieceAt(src).canCapture(getChessPieceAt(dest))||
                ((getChessPieceOwner(src)==PlayerColor.BLUE&&getChessPieceOwner(dest)==PlayerColor.RED)&&
                        ((dest.getRow()==0&&dest.getCol()==2)||(dest.getRow()==1&&dest.getCol()==3)||(dest.getRow()==0&&dest.getCol()==4)))||
                ((getChessPieceOwner(src)==PlayerColor.RED&&getChessPieceOwner(dest)==PlayerColor.BLUE)&&
                        ((dest.getRow()==8&&dest.getCol()==2)||(dest.getRow()==7&&dest.getCol()==3)||(dest.getRow()==8&&dest.getCol()==4))))
            {
                if (getChessPieceAt(src).getRank()==1)
                {
                    if ((src.getRow()==3&&src.getCol()==1)||(src.getRow()==3&&src.getCol()==2)||(src.getRow()==4&&src.getCol()==1)||
                        (src.getRow()==4&&src.getCol()==2)||(src.getRow()==5&&src.getCol()==1)||(src.getRow()==5&&src.getCol()==2)||
                        (src.getRow()==3&&src.getCol()==4)||(src.getRow()==3&&src.getCol()==5)||(src.getRow()==4&&src.getCol()==4)||
                        (src.getRow()==4&&src.getCol()==5)||(src.getRow()==5&&src.getCol()==4)||(src.getRow()==5&&src.getCol()==5))
                    {
                        return false;
                    }
                    else
                    {
                        return calculateDistance(src, dest) == 1;
                    }
                }
                if (getChessPieceAt(src).getRank()==7||getChessPieceAt(src).getRank()==6)
                {
                    if ((src.getRow()==2&&(src.getCol()==1||src.getCol()==2)&&dest.getRow()==6&&dest.getCol()==src.getCol()&&grid[3][src.col].getPiece()==null&&grid[4][src.col].getPiece()==null&&grid[5][src.col].getPiece()==null)||
                        (src.getRow()==2&&(src.getCol()==4||src.getCol()==5)&&dest.getRow()==6&&dest.getCol()==src.getCol()&&grid[3][src.col].getPiece()==null&&grid[4][src.col].getPiece()==null&&grid[5][src.col].getPiece()==null)||
                        (src.getRow()==6&&(src.getCol()==1||src.getCol()==2)&&dest.getRow()==2&&dest.getCol()==src.getCol()&&grid[3][src.col].getPiece()==null&&grid[4][src.col].getPiece()==null&&grid[5][src.col].getPiece()==null)||
                        (src.getRow()==6&&(src.getCol()==4||src.getCol()==5)&&dest.getRow()==2&&dest.getCol()==src.getCol()&&grid[3][src.col].getPiece()==null&&grid[4][src.col].getPiece()==null&&grid[5][src.col].getPiece()==null)||
                        (src.getCol()==0&&(src.getRow()==3||src.getRow()==4||src.getRow()==5)&&dest.getCol()==3&&dest.getRow()==src.getRow()&&grid[src.row][1].getPiece()==null&&grid[src.row][2].getPiece()==null)||
                        (src.getCol()==3&&(src.getRow()==3||src.getRow()==4||src.getRow()==5)&&dest.getCol()==0&&dest.getRow()==src.getRow()&&grid[src.row][1].getPiece()==null&&grid[src.row][2].getPiece()==null)||
                        (src.getCol()==3&&(src.getRow()==3||src.getRow()==4||src.getRow()==5)&&dest.getCol()==6&&dest.getRow()==src.getRow()&&grid[src.row][4].getPiece()==null&&grid[src.row][5].getPiece()==null)||
                        (src.getCol()==6&&(src.getRow()==3||src.getRow()==4||src.getRow()==5)&&dest.getCol()==3&&dest.getRow()==src.getRow()&&grid[src.row][4].getPiece()==null&&grid[src.row][5].getPiece()==null))
                    {
                        return true;
                    }
                    else
                    {
                        return calculateDistance(src,dest)==1;
                    }
                }
                return calculateDistance(src, dest) == 1;
            }
        // TODO:Fix this method
        return false;
    }
}
