import java.util.ArrayList;
import java.util.List;

// row = x, col = y 
public class Plateau
{                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
    private List<Piece> plateau;
    private int SIZE;


    public Plateau(int SIZE){
        this.SIZE = SIZE;
        plateau = new ArrayList<Piece>(SIZE * SIZE);
        for (int i = 0; i < SIZE * SIZE; i++) 
        {
            plateau.add(new Piece("NULL", i / SIZE, i % SIZE, "NULL"));    
        }
    }

    public void viderCase(int x, int y)
    {
        plateau.get(x * SIZE + y).name = "NULL";
        plateau.get(x * SIZE + y).Couleur = "NULL";
        plateau.get(x * SIZE + y).PositionXinit = -1;
        plateau.get(x * SIZE + y).PositionYinit = -1;
    }


    List<Piece> PieceNoir; // Liste de piece noir
    List<Piece> PieceBlanc; // Liste de pièce Blanche

    public String casse(int x, int y)
    {
        int index = x * SIZE + y;
        if("NULL".equals(plateau.get(index).Couleur)) // si la piece à la case index n'a pas de couleur -> pas de piece
        {
            return "NULL";
        }   
        else if("BLANC".equals(plateau.get(index).Couleur)) // sinon, si la pièce sur la case de coordonnée x,y du plateau est blanche, on retourne blanc
        {
            return "BLANC";
        }
        else // sinon, c'est qu'elle est noir
        {
            return "NOIR";
        }  
    }
    // Méthode pour vérifier si les coordonnées sont dans les limites du plateau
    public boolean estDansLesLimites(int x, int y) {
        return (x >= 0 && x < this.SIZE && y >= 0 && y < this.SIZE);
    } 
    public void deplacementPiece(int xactu, int yactu,int xnew,int ynew)
    {
        if(!estDansLesLimites(xnew, ynew) ) // verification que le ocup est poàçssible 
        {
            System.out.println("Déplacement hors des limites du plateau !");
        }
        else
        {
            // on prend les info de l'ancienne case, et on les places dans la nouvelles case
            plateau.get(ynew * SIZE + xnew).name = plateau.get(yactu * SIZE + xactu).name;
            plateau.get(ynew * SIZE + xnew).Couleur = plateau.get(yactu * SIZE + xactu).Couleur;!
            plateau.get(ynew * SIZE + xnew).PositionXinit = plateau.get(yactu * SIZE + xactu).PositionXinit;
            plateau.get(ynew * SIZE + xnew).PositionYinit = plateau.get(yactu * SIZE + xactu).PositionYinit;

            //supprime les info de la case terminé
            viderCase(xactu,yactu);

            System.out.println("Déplacement réussi !");
        }
    }

}

