import java.util.ArrayList;

//TODO : Retirer les cases avec Echec

public class Roi extends Piece implements regle_Piece {
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
    public ArrayList<coordonnee> casesPossiblesJouable(int xactu, int yactu) {
        ArrayList<coordonnee> coords = new ArrayList<>();
        
        // Directions de déplacement du roi dans les coordonnées X et Y
        // X = de gauche à droite
        // Y = de haut en bas 

        int[][] directions = {
            {1, -1}, // haut gauche
            {1, 1},  // haut droite
            {-1, -1}, // bas  gauche
            {-1, 1},  // bas  droite
            {-1, 0}, // Haut
            {1, 0},  // Bas
            {0, -1}, // Gauche
            {0, 1}   // Droite
        };
        
        Piece origin = plateau.getPiece(xactu, yactu);

        for (int[] direction : directions) 
        {
            
            int Y = direction[0];
            int X = direction[1];
            
            int xnew = xactu;
            int ynew = yactu;
            
            // Avancer dans la direction
            xnew += Y;
            ynew += X;
            
            // On vérifie si la pièce sort des limites
            if (plateau.estDansLesLimites(xnew, ynew)) 
            {
                Piece piece = plateau.getPiece(xnew, ynew);
                if (piece.getCouleur() != origin.getCouleur() )//&& DéplacementSécurisé(origin,xnew,ynew)) 
                {
                    coords.add(new coordonnee(xnew, ynew));
                } 
            }
        }
        
        return coords;
    }
    

    // Fonction qui permet de savoir si le déplacement du roi n'entraine pas un éche
    // c-a-d le roi ne sera pas mangé par une piece adverse
        /**
     * Test si le roi est en echec
     * @return resultat du test
     */
    public boolean DéplacementSécurisé(Piece roi, int xnew, int ynew){
        ArrayList<Piece> atester = plateau.getPlateauPiece();
        int x,y;
        for(Piece pi : atester){
            x = pi.getPositionX();
            y = pi.getPositionY();
    
            ArrayList<coordonnee> casesAccessibles;

            if(pi instanceof Tour)
            {
                casesAccessibles =  ((Tour) pi).casesPossiblesJouable(x, y);
            }
            else if(pi instanceof Dame)
            {
                casesAccessibles = ((Dame) pi).casesPossiblesJouable(x, y);
            }
            else if(pi instanceof Fou)
            {
                casesAccessibles = ((Fou) pi).casesPossiblesJouable(x, y);
            }
            else if(pi instanceof Pion)
            {
                casesAccessibles = ((Pion) pi).casesPossiblesJouable(x, y);
            }
            else if(pi instanceof Cavalier)
            {
                casesAccessibles = ((Cavalier) pi).casesPossiblesJouable(x, y);
            }
            else{
                continue;
            }

            // Vérifie si le roi se trouvera dans une case accessible par l'adversaire
            for (coordonnee coord : casesAccessibles) {
                if (coord.getX() == xnew && coord.getY() == ynew) {
                    return false; // Le déplacement met le roi en échec
                }
            }
        }
        return true;
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
