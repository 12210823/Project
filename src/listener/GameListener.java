package listener;

import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;

import java.util.ArrayList;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);


    void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent Chess, ArrayList<CellComponent> list);

}
