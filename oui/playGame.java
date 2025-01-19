import java.util.ArrayList;
import java.util.Scanner;

public class playGame {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le jeu d'échecs !");
        echecs();
    }
    public static void echecs() {
        // Taille standard du plateau d'échecs
        Scanner scanner = new Scanner(System.in);
        int SIZE = -1;
        while(SIZE<4 || SIZE>8)
        {
            System.out.println("quel taille de plateau souhaité vous ? (8x8) à (4x4)");
            System.out.print("Taille : ");
            SIZE = scanner.nextInt();
            if (SIZE<4 || SIZE>8)
            {
                System.out.println("Taille de plateau incorrect. Veillez reessayer \n");
            }
        }

        Plateau plateau = Plateau.initJeu(SIZE);

        // Scanner pour les entrées utilisateur
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
                    ArrayList<coordonnee> coords = ((regle_Piece) piece).casesPrenable(xactu, yactu); 

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
                        if(xnew == 0 || xnew == 7)
                        {
                            plateau.promouvoirPiece(plateau,xnew,ynew);
                        }
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
