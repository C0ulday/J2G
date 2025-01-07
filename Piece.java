import java.util.List;

public class Piece {
    String name;        // Nom de la pièce
    String Couleur;     // Couleur de la pièce
    int positionXinit;  // Coordonnée horizontale initiale
    int positionYinit;  // Coordonnée verticale initiale

    // Constructeur
    public Piece(String name, int positionXinit, int positionYinit, String Couleur) {
        this.name = name;
        this.positionXinit = positionXinit;
        this.positionYinit = positionYinit;
        this.Couleur = Couleur;
    }

    // Getters et Setters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCouleur() {
        return this.Couleur;
    }

    public void setCouleur(String Couleur) {
        this.Couleur = Couleur;
    }

    public int getPositionXinit() {
        return this.positionXinit;
    }

    public void setPositionXinit(int positionXinit) {
        this.positionXinit = positionXinit;
    }

    public int getPositionYinit() {
        return this.positionYinit;
    }

    public void setPositionYinit(int positionYinit) {
        this.positionYinit = positionYinit;
    }
}
