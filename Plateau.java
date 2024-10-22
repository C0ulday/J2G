import java.util.List;

private List<character>plateau;
private static final char EMPTY = '.';

public class Plateau
{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    private static final int SIZE = 8; // Taille du plateau (8x8)
    List<Piece> PieceNoir; // Liste de piece noir
    List<Piece> PieceBlanc; // Liste de pièce Blanche

    public String case(int row, int col){
        int index = row+SIZE +col;
        if(plateau.get(index)!= "EMPTY")
        {
            if(CouleurPiece(x,y) == "BLANC") // si la pièce sur la case de coordonnée x,y du plateau est blanche
            {
                return "BLANC";
            }
            else if(CouleurPiece(x,y) == "NOIR") // si la pièce sur la case de coordonnée x,y du plateau est noire
            {
                return "NOIR";
            }
            else
            {
                return "VIDE";
            }
        }
        
    }
}
