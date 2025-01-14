import java.util.ArrayList;

//TODO : Retirer les cases avec Echec

public class Roi extends Piece implements regle_Piece{
    int xactu, xinit;
    int yactu, yinit;
    private Plateau plateau;

    /* Constructeur 
     * public Piece(String name, int positionXinit, int positionYinit, int positionX, int positionY, String couleur, Plateau plateau) 
    */
    public Roi(int xinit, int yinit, int xactu, int yactu, String couleur, Plateau plateau) {
        super("ROI", xinit, yinit, xactu, yactu, couleur, plateau);
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
    @Override
public ArrayList<coordonnee> casesPossiblesJouable(int xactu, int yactu) {
    ArrayList<coordonnee> coords = new ArrayList<>();
    int[][] directions = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},          {0, 1},
        {1, -1}, {1, 0}, {1, 1}
    };

    for (int[] direction : directions) {
        int xnew = xactu + direction[0];
        int ynew = yactu + direction[1];

        if (plateau.estDansLesLimites(xnew, ynew)) {
            Piece piece = plateau.getPiece(xnew, ynew);
            if ((piece == null || !piece.getCouleur().equals(this.getCouleur())) &&
                deplacementSecurise(this, xnew, ynew)) {
                coords.add(new coordonnee(xnew, ynew));
            }
        }
    }

    return coords;
}

    
    /*Roi : 
    faire les deplacements du Roi au cas ou on a un adversaire au tour de lui
    ce qui l'empechera de se faire manger par une piece adverse (fonction : deplacement securisé)
    on recupere la liste avec toute les pieces adverses et on recupere toutes  les deplacements des pieces adverces
    et toutes les deplacements du roi et des pieces adverses qu'ils ont en commun sont interdites
    la fonction retournera les deplacents ou il n y a pas de danger */
     public boolean deplacementSecurise(Piece roi, int xnew, int ynew) {
        ArrayList<Piece> piecesAdverses = plateau.getPlateauPiece();
    
        for (Piece pieceAdverse : piecesAdverses) {
            // Vérifie si la pièce est adverse
            if (!pieceAdverse.getCouleur().equals(roi.getCouleur())) {
                // Vérifie si la pièce adverse peut attaquer la position cible
                if (pieceAdverse instanceof regle_Piece) {
                    ArrayList<coordonnee> casesAdverses = ((regle_Piece) pieceAdverse).casesPossiblesJouable(
                        pieceAdverse.getPositionX(),
                        pieceAdverse.getPositionY()
                    );
    
                    // Vérifie si la position cible est attaquée
                    for (coordonnee coordAdverse : casesAdverses) {
                        if (coordAdverse.getX() == xnew && coordAdverse.getY() == ynew) {
                            return false; // Le déplacement met le roi en échec
                        }
                    }
                }
    
                // Cas particulier pour les pions (les pions attaquent en diagonale)
                if (pieceAdverse.getName().equals("PION")) {
                    int direction = pieceAdverse.getCouleur().equals("BLANC") ? -1 : 1; // Sens d'attaque du pion
                    int pionX = pieceAdverse.getPositionX();
                    int pionY = pieceAdverse.getPositionY();
    
                    if ((pionX + direction == xnew && pionY + 1 == ynew) ||
                        (pionX + direction == xnew && pionY - 1 == ynew)) {
                        return false; // Le pion attaque la case cible
                    }
                }
            }
        }
    
        return true; // Le déplacement est sécurisé
    }
    
    
    public void afficherCoordsPossibles(int xactu, int yactu) {
        ArrayList<coordonnee> coords = casesPossiblesJouable(xactu, yactu);
    
        System.out.println("Coordonnées possibles pour le Roi :");
        for (coordonnee coord : coords) {
            System.out.println("X : " + coord.getX() + ", Y : " + coord.getY());
        }
    }
    /*/
    public boolean Roque(int x, int y)
    {
        // TODO: créer la fonction roque si on a le temps

        return true;
    }*/
}

