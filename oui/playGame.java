import java.util.Scanner;

public class playGame {
    public static void main(String[] args) {
        // Taille standard du plateau d'échecs
        final int SIZE = 8;
        Plateau plateau = new Plateau(SIZE);

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

        // Scanner pour les entrées utilisateur
        Scanner scanner = new Scanner(System.in);
        boolean partieEnCours = true;
        String joueurActuel = "BLANC";

        // Boucle principale de jeu
        while (partieEnCours) {
            plateau.afficherPlateau(); // Affichage initial
            System.out.println("C'est au tour des " + joueurActuel + "s.");
            
            int xactu, yactu;
            Piece piece = null;
        
            // Boucle pour s'assurer qu'une pièce valide est sélectionnée
            while (true) {
                System.out.println("Entrez les coordonnées de la pièce à déplacer (xactu yactu) :");
                xactu = scanner.nextInt();
                yactu = scanner.nextInt();
                
                piece = plateau.getPiece(xactu, yactu);
        
                if (piece != null && piece.getCouleur().equals(joueurActuel)) {
                    break; // Une pièce valide a été sélectionnée
                } else {
                    System.out.println("Aucune pièce valide à cette position. Veuillez réessayer.");
                }
            }
        
            // Affiche les coordonnées possibles de la pièce sélectionnée
            if (piece instanceof regle_Piece) {
                ((regle_Piece) piece).afficherCoordsPossibles(xactu, yactu);
            } else {
                System.out.println("Cette pièce n'a pas de règles définies.");
                continue; // Redemander les coordonnées de départ
            }
        
            // Boucle pour s'assurer que la destination est valide
            while (true) {
                System.out.println("Entrez les coordonnées de destination (xnew ynew) :");
                int xnew = scanner.nextInt();
                int ynew = scanner.nextInt();
        
                if (plateau.deplacementDansPlateau(xactu, yactu, xnew, ynew)) {
                    plateau.deplacementPiece(xactu, yactu, xnew, ynew);
                    break; // Déplacement effectué, sortir de la boucle
                } else {
                    System.out.println("Déplacement interdit. Veuillez entrer une destination valide.");
                }
            }
        
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
        
            // Changer de joueur
            joueurActuel = joueurActuel.equals("BLANC") ? "NOIR" : "BLANC";
        }
        

        scanner.close();
        System.out.println("Fin de la partie. Merci d'avoir joué !");
    }
}
