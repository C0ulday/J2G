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

     @Override
     public ArrayList<coordonnee> casesPossibles(int xactu, int yactu) {
         ArrayList<coordonnee> coords = new ArrayList<>();
         int dir = (this.getCouleur().equals("NOIR")) ? 1 : -1;
     
         // Vérifie si la case devant est libre
         if (plateau.estDansLesLimites(xactu + dir, yactu) && plateau.isCaseOccupee(xactu + dir, yactu, "NULL")) {
             coords.add(new coordonnee(xactu + dir, yactu));
             // Déplacement initial de 2 cases
             if (xactu == this.getPositionXinit() && plateau.isCaseOccupee(xactu + 2 * dir, yactu, "NULL")) {
                 coords.add(new coordonnee(xactu + 2 * dir, yactu));
             }
         }
     
         // Capture diagonale gauche
         if (plateau.estDansLesLimites(xactu + dir, yactu - 1) && 
             plateau.isCaseOccupee(xactu + dir, yactu - 1, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR")) {
             coords.add(new coordonnee(xactu + dir, yactu - 1));
         }
     
         // Capture diagonale droite
         if (plateau.estDansLesLimites(xactu + dir, yactu + 1) && 
             plateau.isCaseOccupee(xactu + dir, yactu + 1, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR")) {
             coords.add(new coordonnee(xactu + dir, yactu + 1));
         }
     
         return coords;
     }
     


    @Override
    public ArrayList<coordonnee> casesPrenable(int xactu, int yactu) {
        ArrayList<coordonnee> coords = new ArrayList<>();

        int dir = (this.getCouleur().equals("NOIR")) ? -1 : 1;

        if(plateau.isCaseOccupee(xactu+1, yactu+dir, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR" ))
        {
            coords.add(new coordonnee(xactu+1, yactu+dir));
        }

        if(plateau.isCaseOccupee(xactu-1, yactu+dir, this.getCouleur().equals("NOIR") ? "BLANC" : "NOIR" ))
        {
            coords.add(new coordonnee(xactu+1, yactu+dir));
        }    
        
        return coords;
    }

    @Override
    public void afficherCoordsPossibles(int xactu, int yactu) {
        ArrayList<coordonnee> coords = casesPossibles(xactu, yactu);
    
        System.out.println("Coordonnées possibles pour le Pion en ["+xactu+","+yactu+"] :");
        for (coordonnee coord : coords) {
            System.out.println("X : " + coord.getX() + ", Y : " + coord.getY());
        }
    }
    


    /* TODO: gérer la promotion ???
    public boolean Promotion(int xactu) {
        int derniereLigne = (this.getCouleur().equals("blanc")) ? plateau.SIZE() - 1 : 0;
        return xactu == derniereLigne;
    }
    */
}
