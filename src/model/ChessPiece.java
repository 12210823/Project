package model;


import java.io.Serializable;

public class ChessPiece implements Serializable {
    // the owner of the chess
    public PlayerColor owner;

    // Elephant? Cat? Dog? ...
    public String name;
    public int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if (getOwner()!=target.getOwner()&&((getRank()>=target.getRank()&&(getRank()-8)*(getRank()-8)+(target.getRank()-1)*(target.getRank()-1)!=0)
                ||(getRank()==1&&target.getRank()==8)))
        {
            return true;
        }
        // TODO: Finish this method!
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public int getRank() {
        return rank;
    }
}
