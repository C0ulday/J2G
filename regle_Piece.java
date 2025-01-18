import java.util.ArrayList;

public interface regle_Piece {
    ArrayList<coordonnee> casesPossibles(int xactu, int yactu);
    void afficherCoordsPossibles(int xactu, int yactu);
    ArrayList<coordonnee> casesPrenable(int xactu, int yactu);

    // Méthodes pour accéder aux coordonnées actuelles et initiales
    int getPositionX();
    int getPositionY();
    int getPositionXinit();
    int getPositionYinit();

    // Méthodes pour la couleur et le nom
    String getCouleur();
    String getName();
}

