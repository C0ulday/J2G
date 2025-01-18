import java.util.ArrayList;
import java.util.List;

public class Plateau {
    private List<regle_Piece> plateau; // Liste des pièces du plateau
    private int SIZE;

    private List<regle_Piece> piecesNoires; // Liste des pièces du joueur noir
    private List<regle_Piece> piecesBlanches; // Liste des pièces du joueur blanc

    // Constructeur
    public Plateau(int SIZE) {
        this.SIZE = SIZE;
        plateau = new ArrayList<>(SIZE * SIZE);

        // Initialisation du plateau avec des cases vides
        for (int i = 0; i < SIZE * SIZE; i++) {
            plateau.add(null); // Aucune pièce au départ
        }

        piecesNoires = new ArrayList<>();
        piecesBlanches = new ArrayList<>();
    }

    public boolean isCaseOccupee(int x, int y, String couleur) {
        // Vérifie si la case est dans les limites
        if (!estDansLesLimites(x, y)) {
            return false;
        }
    
        // Récupère la pièce à la position donnée
        regle_Piece piece = getPiece(x, y);
    
        // Vérifie si la case est occupée et si la couleur correspond
        if (piece != null) {
            if ("NULL".equals(couleur)) {
                return true; // La case est occupée, peu importe la couleur
            }
            return piece.getCouleur().equals(couleur);
        }
    
        return false; // La case n'est pas occupée
    }
    

    public void remplirPlateau() {
        // Ajouter les pièces noires
        for (regle_Piece piece : piecesNoires) {
            if (estDansLesLimites(piece.getPositionXinit(), piece.getPositionYinit())) {
                placerPiece(piece, piece.getPositionXinit(), piece.getPositionYinit());
            }
        }

        // Ajouter les pièces blanches
        for (regle_Piece piece : piecesBlanches) {
            if (estDansLesLimites(piece.getPositionXinit(), piece.getPositionYinit())) {
                placerPiece(piece, piece.getPositionXinit(), piece.getPositionYinit());
            }
        }
    }

    public void afficherPlateau() {
        System.out.print("\n  ");
        for (int y = 0; y < SIZE; y++) {
            System.out.print(y + " ");
        }
        System.out.println("Y");

        for (int x = 0; x < SIZE; x++) {
            System.out.print(x + " ");
            for (int y = 0; y < SIZE; y++) {
                regle_Piece piece = getPiece(x, y);
                if (piece == null) {
                    System.out.print(". ");
                } else {
                    char affichage = "BLANC".equals(piece.getCouleur()) 
                        ? piece.getName().toUpperCase().charAt(0) 
                        : piece.getName().toLowerCase().charAt(0);
                    System.out.print(affichage + " ");
                }
            }
            System.out.println();
        }
        System.out.println("X");
    }

    public void ajouterPieceNoire(regle_Piece piece) {
        if (piece != null) {
            piecesNoires.add(piece);
        }
    }

    public void ajouterPieceBlanche(regle_Piece piece) {
        if (piece != null) {
            piecesBlanches.add(piece);
        }
    }

    public void viderCase(int x, int y) {
        if (estDansLesLimites(x, y)) {
            plateau.set(x * SIZE + y, null);
        }
    }

    public regle_Piece getPiece(int x, int y) {
        if (estDansLesLimites(x, y)) {
            return plateau.get(x * SIZE + y);
        }
        return null;
    }


    public boolean deplacementDansPlateau(int xactu, int yactu, int xnew, int ynew) {
        // Vérifie si la case de destination est dans les limites du plateau
        if (!estDansLesLimites(xnew, ynew)) {
            System.out.println("Déplacement interdit : Hors des limites.");
            return false;
        }
    
        // Récupère la pièce à la position actuelle
        regle_Piece piece = getPiece(xactu, yactu);
    
        // Si aucune pièce n'est présente à la position initiale
        if (piece == null) {
            System.out.println("Aucune pièce à déplacer à cette position.");
            return false;
        }
    
        // Récupère les mouvements possibles pour la pièce
        ArrayList<coordonnee> casesPossibles = piece.casesPossibles(xactu, yactu);
    
        // Vérifie si la destination est dans les mouvements possibles
        for (coordonnee coord : casesPossibles) {
            if (coord.getX() == xnew && coord.getY() == ynew) {
                return true;
            }
        }
    
        System.out.println("Déplacement interdit : La case cible n'est pas valide pour " + piece.getName());
        return false;
    }
    

    public void deplacementPiece(int xactu, int yactu, int xnew, int ynew) {
        if (deplacementDansPlateau(xactu, yactu, xnew, ynew)) {
            regle_Piece piece = getPiece(xactu, yactu);
            regle_Piece destination = getPiece(xnew, ynew);

            if (destination != null && !destination.getCouleur().equals(piece.getCouleur())) {
                viderCase(xnew, ynew);
            }

            piece.setPosition(xnew, ynew);
            placerPiece(piece, xnew, ynew);
            viderCase(xactu, yactu);
        } else {
            System.out.println("Déplacement interdit.");
        }
    }

    public boolean estDansLesLimites(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    public void placerPiece(regle_Piece piece, int x, int y) {
        if (estDansLesLimites(x, y)) {
            plateau.set(x * SIZE + y, piece);
        }
    }

    public int getSIZE() {
        return this.SIZE;
    }

    public List<regle_Piece> getPlateauPiece() {
        List<regle_Piece> pieces = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                regle_Piece piece = getPiece(i, j);
                if (piece != null) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }
    
    
}
