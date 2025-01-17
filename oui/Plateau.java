import java.util.ArrayList;
import java.util.List;

public class Plateau {
    private List<Piece> plateau;
    private int SIZE;

    private List<Piece> piecesNoires; // Liste des pièces du joueur noir
    private List<Piece> piecesBlanches; // Liste des pièces du joueur blanc

    // Constructeur
    public Plateau(int SIZE) {
        this.SIZE = SIZE;
        plateau = new ArrayList<>(SIZE * SIZE);

        // Initialisation du plateau avec des cases vides
        for (int i = 0; i < SIZE * SIZE; i++) {
            plateau.add(new Piece("NULL", -1, -1,-1,-1, "NULL",this));
        }

        piecesNoires = new ArrayList<>();
        piecesBlanches = new ArrayList<>();
    }

    public void remplirPlateau() {
        // Ajouter les pièces noires
        for (Piece piece : piecesNoires) {
            if (estDansLesLimites(piece.getPositionXinit(), piece.getPositionYinit())) {
                plateau.set(piece.getPositionXinit() * SIZE + piece.getPositionYinit(), piece);
            }
        }

        // Ajouter les pièces blanches
        for (Piece piece : piecesBlanches) {
            if (estDansLesLimites(piece.getPositionXinit(), piece.getPositionYinit())) {
                plateau.set(piece.getPositionXinit() * SIZE + piece.getPositionYinit(), piece);
            }
        }
    }

    public void afficherPlateau() {
        // Affichage des numéros de colonnes avec un espace pour alignement
        System.out.print("\n  ");
        for (int y = 0; y < SIZE; y++) {
            System.out.print(y + " ");
        }
        System.out.println("Y"); // Ajout de l'indicateur Y à droite
    
        // Affichage du plateau avec numéros de lignes
        for (int x = 0; x < SIZE; x++) {
            System.out.print(x + " "); // Affiche le numéro de ligne
            for (int y = 0; y < SIZE; y++) {
                Piece piece = plateau.get(x * SIZE + y);
                if ("NULL".equals(piece.getName())) {
                    System.out.print(". "); // Case vide
                } else {
                    char affichage = "BLANC".equals(piece.getCouleur()) 
                        ? piece.getName().toUpperCase().charAt(0) // Majuscule pour les pièces blanches
                        : piece.getName().toLowerCase().charAt(0); // Minuscule pour les pièces noires
                    System.out.print(affichage + " "); // Affiche la pièce
                }
            }
            System.out.println(); // Nouvelle ligne après chaque rangée
        }
    
        // Ajout de l'indicateur X sous le dernier numéro de colonne
        System.out.println("X"); // Affiche l'indicateur X sous le dernier numéro
    }
    
    

    // Ajoute une pièce à la liste du joueur noir
    public void ajouterPieceNoire(Piece piece) {
        piecesNoires.add(piece);
    }

    // Ajoute une pièce à la liste du joueur blanc
    public void ajouterPieceBlanche(Piece piece) {
        piecesBlanches.add(piece);
    }

    public void viderCase(int x, int y) 
    {
        // Remplace la pièce à la position donnée par une pièce neutre ("NULL")
        plateau.set(x * SIZE + y, new Piece("NULL", -1, -1, -1, -1, "NULL", this));
    }
    
    public Piece getPiece(int x, int y) 
    {
        if (estDansLesLimites(x, y)) 
        {
            return plateau.get(x * SIZE + y);
        }
        return null; // Retourne null si les coordonnées sont en dehors des limites
    }
    /*
    jouer permet de jouer
    */
    public void jouerPiece()
    {
    }
    public boolean deplacementDansPlateau(int xactu, int yactu, int xnew, int ynew) {
        // Vérifie si les coordonnées cibles sont dans les limites
        if (!estDansLesLimites(xnew, ynew)) {
            System.out.println("Déplacement interdit !");
            return false;
        }
    
        // Récupère la pièce à déplacer
        Piece piece = getPiece(xactu, yactu);
    
        // Vérifie si une pièce est présente à la position actuelle
        if (piece == null || "NULL".equals(piece.getName())) {
            System.out.println("Aucune pièce à déplacer à cette position !");
            return false;
        }
    
        // Vérifie si la pièce implémente l'interface regle_Piece
        if (!(piece instanceof regle_Piece)) {
            System.out.println("Cette pièce ne peut pas être déplacée (pas de règles associées).");
            return false;
        }
    
        // Obtenir les cases possibles pour cette pièce
        ArrayList<coordonnee> casesPossibles = ((regle_Piece) piece).casesPossibles(xactu, yactu);
    
        // Vérifier si la case cible est autorisée
        boolean deplacementValide = false;
        for (coordonnee coord : casesPossibles) {
            if (coord.getX() == xnew && coord.getY() == ynew) {
                deplacementValide = true;
                break;
            }
        }
    
        if (!deplacementValide) {
            System.out.println("Déplacement interdit : La case cible n'est pas dans les mouvements possibles !");
            return false;
        }
    
        //System.out.println("Déplacement validé !");
        return true;
    }
    

    public void deplacementPiece(int xactu, int yactu, int xnew, int ynew) {
        // Vérifie si le déplacement est autorisé par `deplacementDansPlateau`
        if (deplacementDansPlateau(xactu, yactu, xnew, ynew)) {
            // Récupère la pièce à déplacer
            Piece piece = getPiece(xactu, yactu);
            Piece destination = getPiece(xnew, ynew);
    
            // Si la destination contient une pièce ennemie, elle est capturée
            if (destination != null && !destination.getCouleur().equals(piece.getCouleur())) {
                viderCase(xnew, ynew);
            }
    
            // Met à jour les coordonnées de la pièce
            piece.setPosition(xnew, ynew);
    
            // Place la pièce sur la nouvelle case en appelant `placerPiece`
            placerPiece(piece, xnew, ynew);
    
            // Vide l'ancienne case
            viderCase(xactu, yactu);
    
            //System.out.println("Déplacement réussi !");
        } else {
            System.out.println("Déplacement interdit.");
        }
    }
    
    
    
    

    public boolean estDansLesLimites(int x, int y) 
    {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    public boolean isCaseOccupee(int x, int y, String couleur) {
        if (!estDansLesLimites(x, y)) { // Vérifie si les indices sont valides
            return false;
        }
        Piece piece = plateau.get(x * SIZE + y);
        return piece != null && piece.getCouleur().equals(couleur);
    }

    public void placerPiece(Piece piece, int x, int y) 
    {
        if (estDansLesLimites(x,y))
        {
            plateau.set(x * SIZE + y, piece);
            //System.out.println("Pièce placee avec succes");
        }
        else
        {
            //System.out.println("coordonnées invalide");
        }
        
    }

    public void PrisePiece(int x, int y)
    {
        //TODO réaliser la fonction

        //Piece piece = getPiece(x,y);

    }

    public ArrayList<Piece> getPlateauPiece()
    {
        ArrayList<Piece> p = new ArrayList<Piece>();
        
        int x,y;

        for(int i=0; i<SIZE*SIZE;i++)
        {
            x = i / SIZE;
            y = i % SIZE;
            if(this.getPiece(x,y) != null)
            {
                p.add(this.getPiece(x, y));
            }
        }
        return p;
    }

    public int getSIZE() {

        return this.SIZE;
    }

    public boolean verifEchec(Plateau plateau, Piece piece, int x, int y, int xactu, int yactu) {
        // Sauvegarder la pièce à la position cible
        Piece pieceDepart = getPiece(xactu, yactu);
        Piece pieceArrive = getPiece(x, y);
    
        // Effectuer le déplacement temporaire
        plateau.deplacementPiece(xactu, yactu, x, y);

    
        // Vérifier si l'échec est toujours présent après le déplacement
        //System.out.println("x= "+x+" y= "+y+" xactu= "+xactu+" yactu= "+yactu+" Couleur piece à la case ="+pieceDepart.getCouleur());
        boolean echec = regleJeuEchec.Echec(plateau, pieceDepart.getCouleur());
    
        // Restaurer l'état initial
        plateau.deplacementPiece(x, y, xactu, yactu);
        plateau.placerPiece(pieceArrive, x, y);
    
        return echec;
    }

}