import java.util.HashSet;
import java.util.Set;

public class Jeu8Dames {
    public static void jouer() {
        final int SIZE = 8;
        int[] solution = new int[SIZE]; // Chaque index représente une colonne, la valeur représente la ligne
        Set<String> solutionsTrouvees = new HashSet<>(); // Pour stocker les solutions uniques
        placerDame(solution, 0, SIZE, solutionsTrouvees);
        System.out.println("Nombre total de solutions uniques trouvées : " + solutionsTrouvees.size());
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
        for (int ligne : solution) {
            for (int colonne = 0; colonne < solution.length; colonne++) {
                if (solution[colonne] == ligne) {
                    System.out.print("D ");
                } else {
                    System.out.print(". ");
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
