package model;

import java.io.Serializable;

public class Steps implements Serializable {
    public int turn;
    public ChessboardPoint src;
    public ChessboardPoint dest;
    public ChessPiece srcPiece;
    public ChessPiece destPiece;
    public Steps(ChessPiece srcPiece,ChessPiece destPiece,ChessboardPoint src,ChessboardPoint dest,int turn)
    {
        this.srcPiece=srcPiece;
        this.destPiece=destPiece;
        this.src=src;
        this.dest=dest;
        this.turn=turn;
    }

    public int getTurn() {
        return turn;
    }

    public ChessboardPoint getDest() {
        return dest;
    }

    public ChessboardPoint getSrc() {
        return src;
    }

    public ChessPiece getDestPiece() {
        return destPiece;
    }

    public ChessPiece getSrcPiece() {
        return srcPiece;
    }

    public void setDest(ChessboardPoint dest) {
        this.dest = dest;
    }

    public void setDestPiece(ChessPiece destPiece) {
        this.destPiece = destPiece;
    }

    public void setSrc(ChessboardPoint src) {
        this.src = src;
    }

    public void setSrcPiece(ChessPiece srcPiece) {
        this.srcPiece = srcPiece;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "Steps{" +
                "turn=" + turn +
                ", src=" + src +
                ", dest=" + dest +
                ", srcPiece=" + srcPiece +
                ", destPiece=" + destPiece +
                '}';
    }
}
