import java.util.ArrayList;
import java.util.List;

public class Plateau {
    private List<Piece> plateau;
    private int SIZE;

    public Plateau(int SIZE) {
        this.SIZE = SIZE;
        plateau = new ArrayList<Piece>(SIZE * SIZE);
        for (int i = 0; i < SIZE * SIZE; i++) {
            plateau.add(new Piece("NULL", i / SIZE, i % SIZE, i / SIZE, i % SIZE, "NULL", this));
        }
    }

    public void viderCase(int x, int y) {
        // Remplace la pièce à la position donnée par une pièce neutre ("NULL")
        plateau.set(x * SIZE + y, new Piece("NULL", -1, -1, -1, -1, "NULL", this));
    }
    
    public Piece getPiece(int x, int y) {
        if (estDansLesLimites(x, y)) {
            return plateau.get(x * SIZE + y);
        }
        return null; // Retourne null si les coordonnées sont en dehors des limites
    }
    /*
    jouer permet de jouer
    */
    //public void jouer(){
    //}
    public void deplacementPiece(int xactu, int yactu, int xnew, int ynew) {
        if (!estDansLesLimites(xnew, ynew)) { // Vérifie si le déplacement est possible
            System.out.println("Déplacement interdit !");
            return;
        }
    
        // Récupère la pièce à déplacer
        Piece piece = plateau.get(xactu * SIZE + yactu);
    
        if (piece == null || "NULL".equals(piece.getName())) {
            System.out.println("Aucune pièce à déplacer à cette position !");
            return;
        }
        
        if (!piece.coupPossible(xnew, ynew)) {
            System.out.println("Déplacement impossible !");
            return;
        }

        // Met à jour les coordonnées actuelles de la pièce
        piece.setPosition(xnew, ynew);
    
        // Place la pièce à la nouvelle position dans le plateau
        placerPiece(piece, xnew, ynew);
    
        // Vide l'ancienne case
        viderCase(xactu, yactu);


        System.out.println("Déplacement réussi !");
    }
    

    public boolean estDansLesLimites(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    public boolean isCaseOccupee(int x, int y, String couleur) {
        Piece piece = plateau.get(x * SIZE + y);
        return piece != null && piece.getCouleur().equals(couleur);
    }

    public void placerPiece(Piece piece, int x, int y) {
        plateau.set(x * SIZE + y, piece);
    }
    public boolean coupPossible(int x, int y) {
        return this.estDansLesLimites(x, y) && !this.isCaseOccupee(x, y, "NULL"); // "NULL" pour ignorer la couleur ici
    }
}
