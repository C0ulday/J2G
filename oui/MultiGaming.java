import java.util.Scanner;

public class MultiGaming {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            // Afficher le menu principal
            System.out.println("=== MENU PRINCIPAL ===");
            System.out.println("1. Échecs");
            System.out.println("2. Jeu des 8 dames");
            System.out.println("3. Quitter");
            System.out.println("Veuillez choisir un jeu (1-3) :");

            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    // Lancer le jeu d'échecs
                    System.out.println("Vous avez choisi de jouer aux Échecs !");
                    playGame.echecs(); // Appel de la méthode pour jouer aux échecs
                    break;

                case 2:
                    // Lancer le jeu des 8 dames
                    System.out.println("Vous avez choisi de jouer au Jeu des 8 dames !");
                    Jeu8Dames.jouer(); // Appel de la méthode pour jouer au jeu des 8 dames
                    break;

                case 3:
                    // Quitter le programme
                    System.out.println("Merci d'avoir joué ! À bientôt !");
                    continuer = false;
                    break;

                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }

        scanner.close();
    }
}
