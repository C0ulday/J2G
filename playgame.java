import java.util.ArrayList;
import java.util.Scanner;

public class playGame {
    static Plateau plateau;
    static String joueurActuel = "BLANC";

    public static void run() {
        // Taille standard du plateau d'échecs
        final int SIZE = 8;
        plateau = new Plateau(SIZE);

        // Initialisation des pièces blanches
        for (int i = 0; i < 8; i++) {
            plateau.ajouterPieceBlanche(new Pion(6, i, 6, i, "BLANC", plateau));
        }
        plateau.ajouterPieceBlanche(new Tour(7, 0, 7, 0, "BLANC", plateau));
        plateau.ajouterPieceBlanche(new Tour(7, 7, 7, 7, "BLANC", plateau));
        plateau.ajouterPieceBlanche(new Cavalier(7, 1, 7, 1, "BLANC", plateau));
        plateau.ajouterPieceBlanche(new Cavalier(7, 6, 7, 6, "BLANC", plateau));
        plateau.ajouterPieceBlanche(new Fou(7, 2, 7, 2, "BLANC", plateau));
        plateau.ajouterPieceBlanche(new Fou(7, 5, 7, 5, "BLANC", plateau));
        plateau.ajouterPieceBlanche(new Dame(7, 3, 7, 3, "BLANC", plateau));
        plateau.ajouterPieceBlanche(new Roi(7, 4, 7, 4, "BLANC", plateau));

        // Initialisation des pièces noires
        for (int i = 0; i < 8; i++) {
            plateau.ajouterPieceNoire(new Pion(1, i, 1, i, "NOIR", plateau));
        }
        plateau.ajouterPieceNoire(new Tour(0, 0, 0, 0, "NOIR", plateau));
        plateau.ajouterPieceNoire(new Tour(0, 7, 0, 7, "NOIR", plateau));
        plateau.ajouterPieceNoire(new Cavalier(0, 1, 0, 1, "NOIR", plateau));
        plateau.ajouterPieceNoire(new Cavalier(0, 6, 0, 6, "NOIR", plateau));
        plateau.ajouterPieceNoire(new Fou(0, 2, 0, 2, "NOIR", plateau));
        plateau.ajouterPieceNoire(new Fou(0, 5, 0, 5, "NOIR", plateau));
        plateau.ajouterPieceNoire(new Dame(0, 3, 0, 3, "NOIR", plateau));
        plateau.ajouterPieceNoire(new Roi(0, 4, 0, 4, "NOIR", plateau));

        // Remplir le plateau avec les pièces
        plateau.remplirPlateau();

        System.out.println("Backend prêt à recevoir des mouvements.");
    }

    // Méthode pour traiter un mouvement (format \"xactu yactu xnew ynew\")
    public static boolean  traiterMouvement(String mouvement) {
        String[] parts = mouvement.split(" ");
        int xactu = Integer.parseInt(parts[0]);
        int yactu = Integer.parseInt(parts[1]);
        int xnew = Integer.parseInt(parts[2]);
        int ynew = Integer.parseInt(parts[3]);

        Piece piece = plateau.getPiece(xactu, yactu);

        if (piece == null || !piece.getCouleur().equals(joueurActuel)) {
            return false;
        }

        if (plateau.deplacementDansPlateau(xactu, yactu, xnew, ynew)) {
            plateau.deplacementPiece(xactu, yactu, xnew, ynew);
             // Vérifications des conditions de fin de partie
            if (regleJeuEchec.Echec(plateau, joueurActuel)) {
                System.out.println("Attention : le roi des " + joueurActuel + "s est en échec !");
            }

            if (regleJeuEchec.Mat(plateau, joueurActuel)) {
                System.out.println("Échec et mat ! Les " + (joueurActuel.equals("BLANC") ? "Noirs" : "Blancs") + "s gagnent !");
                partieEnCours = false;
                continue; // Quitter le tour
            }

            if (regleJeuEchec.Pat(plateau, joueurActuel)) {
                System.out.println("Pat ! Match nul.");
                partieEnCours = false;
                continue; // Quitter le tour
            }
            joueurActuel = joueurActuel.equals("BLANC") ? "NOIR" : "BLANC";
            return true;
        } else {
            return false;
        }

        
    }

    public static  ArrayList<coordonnee> obtenirCoupPossible(int x, int y){
        Piece piece = plateau.getPiece(x, y);

        ArrayList<coordonnee> cords = ((regle_Piece) piece).casesPossibles(x,y);

        return cords;
    }
}


