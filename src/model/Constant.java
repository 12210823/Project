package model;

public enum Constant {
    CHESSBOARD_ROW_SIZE(9),CHESSBOARD_COL_SIZE(7);

    public final int num;
    Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }
}
