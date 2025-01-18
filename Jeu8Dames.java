import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Jeu8Dames {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le jeu des 8 dames !");
        jouer8Dames();
    }

    public static void jouer8Dames() {
        final int SIZE = 8;
        int[] solution = new int[SIZE]; // Chaque index représente une colonne, la valeur représente la ligne
        Set<String> solutionsTrouvees = new HashSet<>(); // Pour stocker les solutions uniques

        Scanner scanner = new Scanner(System.in);
        boolean jeuEnCours = true;

        while (jeuEnCours) {
            System.out.println("=== Menu Jeu des 8 dames ===");
            System.out.println("1. Trouver et afficher toutes les solutions");
            System.out.println("2. Quitter");
            System.out.print("Veuillez choisir une option : ");
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    System.out.println("Calcul des solutions...");
                    solutionsTrouvees.clear(); // Réinitialiser les solutions précédentes
                    placerDame(solution, 0, SIZE, solutionsTrouvees);
                    System.out.println("Nombre total de solutions uniques trouvées : " + solutionsTrouvees.size());
                    break;
                case 2:
                    System.out.println("Merci d'avoir joué au jeu des 8 dames !");
                    jeuEnCours = false;
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
                    break;
            }
        }

        scanner.close();
    }

    private static boolean placerDame(int[] solution, int colonne, int taille, Set<String> solutionsTrouvees) {
        if (colonne == taille) {
            // Générer une représentation unique de la solution
            String representation = genererRepresentation(solution);
            if (!solutionsTrouvees.contains(representation)) {
                solutionsTrouvees.add(representation); // Ajouter la solution unique
                afficherSolution(solution);
            }
            return true;
        }

        boolean auMoinsUneSolution = false;

        for (int ligne = 0; ligne < taille; ligne++) {
            if (estValide(solution, colonne, ligne)) {
                solution[colonne] = ligne;
                auMoinsUneSolution |= placerDame(solution, colonne + 1, taille, solutionsTrouvees);
            }
        }

        return auMoinsUneSolution;
    }

    private static boolean estValide(int[] solution, int colonne, int ligne) {
        for (int i = 0; i < colonne; i++) {
            int autreLigne = solution[i];
            if (autreLigne == ligne || Math.abs(autreLigne - ligne) == Math.abs(i - colonne)) {
                return false; // Même ligne ou diagonale
            }
        }
        return true;
    }

    private static void afficherSolution(int[] solution) {
        System.out.println("=== Solution trouvée ===");
        for (int ligne = 0; ligne < solution.length; ligne++) {
            for (int colonne = 0; colonne < solution.length; colonne++) {
                if (solution[colonne] == ligne) {
                    // Alternance entre dames noires et blanches pour plus de réalisme
                    if ((ligne + colonne) % 2 == 0) {
                        System.out.print("N "); // Dame noire
                    } else {
                        System.out.print("B "); // Dame blanche
                    }
                } else {
                    if ((ligne + colonne) % 2 == 0) {
                        System.out.print(". "); // Case noire
                    } else {
                        System.out.print("  "); // Case blanche
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String genererRepresentation(int[] solution) {
        StringBuilder sb = new StringBuilder();
        for (int pos : solution) {
            sb.append(pos).append("-");
        }
        return sb.toString();
    }
}
