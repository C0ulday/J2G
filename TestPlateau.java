public class TestPlateau {
    public static void main(String[] args) {
        Plateau plateau = new Plateau(8); // Crée un plateau de taille 8x8

        // Déplace une pièce de (1, 1) à (2, 1)
        plateau.deplacementPiece(1, 1, 2, 1);

        // Vérifie si la case (2, 1) contient la pièce
        System.out.println("Case (2,1) : " + plateau.casse(2, 1));

        // Essaye de déplacer une pièce hors des limites
        plateau.deplacementPiece(7, 7, 8, 8); // Devrait afficher une erreur
    }
}

