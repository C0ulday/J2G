import java.util.ArrayList;
import java.util.Scanner;

public class playGame {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le jeu d'échecs !");
        echecs();
    }
    public static void echecs() {
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
            System.out.println("C'est au tour des " + joueurActuel + "s.");

            int xactu, yactu;
            Piece piece = null;

            // Boucle pour assurer la validité du coup
            boolean choixPiece;
            do{
                choixPiece = true;
                plateau.afficherPlateau(); // Affichage du plateau
                System.out.println("Entrez les coordonnées de la pièce à déplacer (X Y) :");
                xactu = scanner.nextInt();
                yactu = scanner.nextInt();
                piece = plateau.getPiece(xactu, yactu);
                if(!piece.getCouleur().equals(joueurActuel)) // si la piece selectionné n'appartient pas au joueur courant, on reboucle
                {
                    System.out.println("Piece non selectionable ! Veillez recommencer");
                    choixPiece = false;
                }
                else
                {
                    ArrayList<coordonnee> coords = ((regle_Piece) piece).casesPrenable(xactu, yactu); // pb ici avec pion

                    if(coords.isEmpty()) // si une piece valide sélectionné ne peut pas bouger, on reboucle
                    {
                        choixPiece = false;
                        System.out.println("Piece non deplaçable ! Veillez recommencer");
                    }
                }
                
                if (piece instanceof regle_Piece) 
                {
                    ((regle_Piece) piece).afficherCoordsPrenable(xactu, yactu); // on affiche les coup réalisable par la piéce
                }
                if(choixPiece)
                {
                    System.out.println("Entrez les coordonnées de destination (X Y) :");
                    int xnew = scanner.nextInt();
                    int ynew = scanner.nextInt();

                    if (!plateau.deplacementDansPlateau(xactu,yactu, xnew,ynew)) // si la coordonnée d'arrivée est incorrect, on reboucle
                    {
                        choixPiece = false; // Déplacement effectué
                        System.out.println("Déplacement interdit. Veuillez entrer une destination valide.");
                    } 
                    else 
                    { 
                        plateau.deplacementPiece(xactu, yactu, xnew, ynew);
                    }
                }
            }while(!choixPiece); 

            // Changer de joueur
            joueurActuel = joueurActuel.equals("BLANC") ? "NOIR" : "BLANC";


            // Vérifications des conditions de fin de partie
            if (regleJeuEchec.Echec(plateau, joueurActuel)) {
                System.out.println("Attention : le roi " + joueurActuel + " est en échec !");
            }

            if (regleJeuEchec.Mat(plateau, joueurActuel)) {
                System.out.println("Échec et mat ! Les " + (joueurActuel.equals("BLANC") ? "Noirs" : "Blancs") + " gagnent !");
                partieEnCours = false;
                continue; // Quitter le tour
            }

            if (regleJeuEchec.Pat(plateau, joueurActuel)) {
                System.out.println("Pat ! Match nul.");
                partieEnCours = false;
                continue; // Quitter le tour
            }

            
        }

        scanner.close();
        System.out.println("Fin de la partie. Merci d'avoir joué !");
    }
}
