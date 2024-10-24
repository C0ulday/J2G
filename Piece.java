public abstract class Piece {
    protected String couleur;

    public Piece(String couleur) {
        this.couleur = couleur;
    }

    public abstract String getSymbole();
}

private class Tour extends Piece {
    public Tour(String couleur) {
        super(couleur);
    }

    @Override
    public String getSymbole() {
        return couleur.equals("noir") ? "♖" : "♜";
    }
}

// Crée des classes similaires pour Roi, Reine, etc.
