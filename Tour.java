import java.util.ArrayList;

public class Tour extends Piece{
    int xactu, xinit;
    int yactu, yinit;
    private Plateau plateau;

    /* Constructeur 
     * public Piece(String name, int positionXinit, int positionYinit, int positionX, int positionY, String couleur, Plateau plateau) 
    */
    public Tour(int xinit, int yinit, int xactu, int yactu, String couleur, Plateau plateau) {
        super("TOUR", xinit, yinit, xactu, yactu, couleur, plateau);
        this.xactu = xactu;
        this.yactu = yactu;
        this.xinit = xinit;
        this.yinit = yinit;
        this.plateau = plateau;
    }

    /**
     * Verifie si le deplacement est possible
     * @param x L'abscisse d'arrive = les lettres
     * @param y l'ordonnee d'arrive = les chiffres
     * @return resultat du test
     */


    /*
     *** La fonction prend en entrée les positions actuelles de la pièce
     * Vérifie les limites
     * Les déplacements
     * Vérifie si la case qu'on veut aller est nulle ou contient une pièce adverse
     * Les types de déplacements de la pièce
     * On ne peut pas sauter une pièce adverse
     *** La fonction retourne les coordonnées possibles où on peut aller 
     */
    public ArrayList<coordonnee> casesPossibles_Jouable(int xactu, int yactu) {
        ArrayList<coordonnee> coords = new ArrayList<coordonnee>();
        
        // Déplacements possibles pour le cavalier
        int[][] deplacements = {
            {-2, -1}, {-2, 1}, {2, -1}, {2, 1},
            {-1, -2}, {-1, 2}, {1, -2}, {1, 2}
        };

        for (int[] deplacement : deplacements) {
            int newX = xactu + deplacement[0];
            int newY = yactu + deplacement[1];

            if (plateau.estDansLesLimites(newX, newY)) {
                Piece piece = plateau.getPiece(newX, newY);
                if (piece == null || !piece.getCouleur().equals(this.getCouleur())) {
                    coords.add(new coordonnee(newX, newY));
                }
            }
        }
    return coords;
    }

    // Fonction qui permet le premier déplacement de la pièce
    public boolean PremierdeplacementPiece(int positionX, int positionY) {
        return true;
    }

    public boolean estVide(int x, int y) {
        return plateau.getPiece(x, y) == null;
    }
    public void PrisePiece(int x, int y) {
        if (plateau.getPiece(x, y) != null && !plateau.getPiece(x, y).getCouleur().equals(this.getCouleur())) {
            plateau.viderCase(x, y);
        }
    }
}
