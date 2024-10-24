public abstract class Piece {
    protected String couleur;

    public Piece(String couleur) {
        this.couleur = couleur;
    }

    public abstract String getSymbole();
}

//Crée des classes similaires pour Roi, Reine, etc.
