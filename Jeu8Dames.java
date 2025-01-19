import java.util.ArrayList;
import java.util.Scanner;

public class Jeu8Dames {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le jeu des 8 Dames !");
        jeu8Dames();
    }

    public static void jeu8Dames() {
        // Scanner pour les entrées utilisateur
        Scanner scanner = new Scanner(System.in);

        // Choix de la taille du plateau
        int SIZE = -1;
        while (SIZE < 4 || SIZE > 8) {
            System.out.println("Quelle taille de plateau souhaitez-vous ? (entre 4x4 et 8x8)");
            System.out.print("Taille : ");
            SIZE = scanner.nextInt();
            if (SIZE < 4 || SIZE > 8) {
                System.out.println("Taille de plateau incorrecte. Veuillez réessayer.\n");
            }
        }

        // Initialisation du plateau
        Plateau plateau = Plateau.InitJeu8Dames(SIZE);

        // Boucle de jeu (similaire à votre logique existante)
        boolean partieEnCours = true;
        String joueurActuel = "BLANC";

        while (partieEnCours) {
            System.out.println("C'est au tour des " + joueurActuel + "s.");

            plateau.afficherPlateau();
            int xactu, yactu;
            Piece piece;

            boolean choixPiece;
            do {
                choixPiece = true;
                System.out.println("Entrez les coordonnées de la pièce à déplacer (X Y) :");
                xactu = scanner.nextInt();
                yactu = scanner.nextInt();

                piece = plateau.getPiece(xactu, yactu);

                if (piece == null || !piece.getCouleur().equals(joueurActuel)) {
                    System.out.println("Pièce non sélectionnable ! Veuillez recommencer.");
                    choixPiece = false;
                    continue;
                }

                if (piece instanceof regle_Piece) {
                    ArrayList<coordonnee> coords = ((regle_Piece) piece).casesPrenable(xactu, yactu);
                    if (coords.isEmpty()) {
                        System.out.println("Pièce non déplaçable ! Veuillez recommencer.");
                        choixPiece = false;
                    } else {
                        ((regle_Piece) piece).afficherCoordsPrenable(xactu, yactu);
                    }
                }

                if (choixPiece) {
                    System.out.println("Entrez les coordonnées de destination (X Y) :");
                    int xnew = scanner.nextInt();
                    int ynew = scanner.nextInt();

                    if (!plateau.deplacementDansPlateau(xactu, yactu, xnew, ynew)) {
                        System.out.println("Déplacement interdit. Veuillez entrer une destination valide.");
                        choixPiece = false;
                    } else {
                        plateau.deplacementPiece(xactu, yactu, xnew, ynew);
                    }
                }
            } while (!choixPiece);

            joueurActuel = joueurActuel.equals("BLANC") ? "NOIR" : "BLANC";

            if(Plateau.FinDuJeu(plateau,joueurActuel))
            {
                System.out.println("Fin de jeu ! Les " + (joueurActuel.equals("BLANC") ? "Noirs" : "Blancs") + " gagnent !");
                partieEnCours = false;
                continue;
            }
        }

        scanner.close();
        System.out.println("Merci d'avoir joué !");
    }

}