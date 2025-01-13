import java.util.ArrayList;

public class Pion extends Piece implements regle_Piece {
    int xactu, xinit;
    int yactu, yinit;
    private Plateau plateau;

    /* Constructeur 
     * public Piece(String name, int positionXinit, int positionYinit, int positionX, int positionY, String couleur, Plateau plateau) 
    */
    public Pion(int xinit, int yinit, int xactu, int yactu, String couleur, Plateau plateau) {
        super("PION", xinit, yinit, xactu, yactu, couleur, plateau);
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
        ArrayList<coordonnee> coords = new ArrayList<>();
        
        // création de la pièce 

        Piece pion = plateau.getPiece(xactu, yactu);
        int dir = (this.getCouleur().equals("NOIR")) ? -1 : 1;


        if(plateau.isCaseOccupee(xactu, yactu+1, "NULL"))
            {
                coords.add(new coordonnee(xactu, yactu+dir));
                if(pion.getPositionXinit()==xactu && pion.getPositionYinit()==yactu && plateau.isCaseOccupee(xactu, yactu+2, "NULL"))
                {
                    coords.add(new coordonnee(xactu, yactu+2*dir));
                }
            }

            if(plateau.isCaseOccupee(xactu+1, yactu+dir, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR" ))
            {
                coords.add(new coordonnee(xactu+1, yactu+dir));
            }

            if(plateau.isCaseOccupee(xactu-1, yactu+dir, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR" ))
            {
                coords.add(new coordonnee(xactu+1, yactu+dir));
            }
        
        else
        {
            System.out.println("problème de génération de la pièce");
        }
        
        return coords;
    }


   
    


    /* TODO: gérer la promotion ???
    public boolean Promotion(int xactu) {
        int derniereLigne = (this.getCouleur().equals("blanc")) ? plateau.SIZE() - 1 : 0;
        return xactu == derniereLigne;
    }
    */

    public void PrisePiece(int x, int y) {
        if (plateau.getPiece(x, y) != null && !plateau.getPiece(x, y).getCouleur().equals(this.getCouleur())) {
            plateau.viderCase(x, y);
        }
    }
}
