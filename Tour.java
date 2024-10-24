public class Tour extends Piece {
    public Tour(String couleur) {
        super(couleur);
    }

    @Override
    public String getSymbole() {
        return couleur.equals("noir") ? "♖" : "♜";
    }
}