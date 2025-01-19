import java.util.ArrayList;
import java.util.Scanner;
import java.lang.reflect.Constructor;
import java.util.HashMap;

public class Plateau {
    public ArrayList<Piece> plateau;
    public int SIZE;

    public ArrayList<Piece> piecesNoires; // Liste des pièces du joueur noir
    public ArrayList<Piece> piecesBlanches; // Liste des pièces du joueur blanc

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

    public void initPlateau() {
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
    
    public boolean deplacementDansPlateau(int xactu, int yactu, int xnew, int ynew) {
        // Vérifie si les coordonnées cibles sont dans les limites
        if (!estDansLesLimites(xnew, ynew)) {
            //System.out.println("Déplacement interdit !");
            return false;
        }
    
        // Récupère la pièce à déplacer
        Piece piece = getPiece(xactu, yactu);
    
        // Vérifie si une pièce est présente à la position actuelle
        if (piece == null || "NULL".equals(piece.getName())) {
            //System.out.println("Aucune pièce à déplacer à cette position !");
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
            //System.out.println("Déplacement interdit : La case cible n'est pas dans les mouvements possibles !");
            return false;
        }
    
        //System.out.println("Déplacement validé !");
        return true;
    }
    

    public boolean deplacementPiece(int xactu, int yactu, int xnew, int ynew) {
        // Vérifie si le déplacement est autorisé par `deplacementDansPlateau`
        if (deplacementDansPlateau(xactu, yactu, xnew, ynew)) 
        {
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
            return true;
        } 
        else 
        {
            return false;
            //System.out.println("Déplacement interdit.");
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
        plateau.placerPiece(pieceDepart, x, y);
        plateau.viderCase(xactu, yactu);

        // Vérifier si l'échec est toujours présent après le déplacement
        //System.out.println("x= "+x+" y= "+y+" xactu= "+xactu+" yactu= "+yactu+" Couleur piece à la case ="+pieceDepart.getCouleur());
        boolean echec = regleJeuEchec.Echec(plateau, pieceDepart.getCouleur());
        
        // Restaurer l'état initial
        plateau.placerPiece(pieceDepart, xactu, yactu);
        
        
        plateau.placerPiece(pieceArrive, x, y);
    
        //retourne s'il y a echec ou pas
        return echec;
    }

    public void promotion(Plateau plateau,int x,int y) 
    {


        // On vérifie si la piéce peut être promu
        Piece piece = plateau.getPiece(x, y);
        if(!plateau.getPiece(x,y).getName().equals("PION") && (x == 0 || x == 7))
        {
            return;
        }

        // Options de promotion
        ArrayList<String> Pieces = new ArrayList<>();
        Pieces.add("DAME");
        Pieces.add("CAVALIER");
        Pieces.add("FOU");
        Pieces.add("TOUR");

        // interface pour choisir la piece
        System.out.println("Choisissez votre nouvelle pièce :");
        System.out.println("1 = DAME, 2 = CAVALIER, 3 = FOU, 4 = TOUR");

        //choix de la piece
        Scanner scanner = new Scanner(System.in);
        int numPiece;

        // Demander à l'utilisateur de faire un choix valide
        do {
            System.out.print("Entrez un numéro entre 1 et 4 : ");
            numPiece = scanner.nextInt() - 1;
        } while (numPiece < 0 || numPiece > 3);

        // Déterminer le nom de la nouvelle pièce
        String nomPiece = Pieces.get(numPiece);

        // Ajouter la nouvelle pièce au plateau
        
        //chagement de la pièce
        piece.setName(nomPiece);
        piece.setPositionXinit(x);
        piece.setPositionYinit(y);
        if (piece instanceof regle_Piece)
        {

        }
        System.out.println("La pièce a été promue en " + nomPiece + " !");
        scanner.close();
    }



    public void promouvoirPiece(Plateau plateau, int x, int y) 
    {
        // Récupérer la pièce actuelle
        Piece piece = plateau.getPiece(x, y);

        // Vérifier qu'une pièce est présente
        if (piece == null) {
            System.out.println("Aucune pièce à promouvoir à cette position !");
            return;
        }

        // Liste des types de promotion possibles
        HashMap<Integer, Class<? extends Piece>> promotions = new HashMap<>();
        promotions.put(1, Dame.class); // Option 1 : Dame
        promotions.put(2, Tour.class); // Option 2 : Tour
        promotions.put(3, Fou.class); // Option 3 : Fou
        promotions.put(4, Cavalier.class); // Option 4 : Cavalier

        // Afficher les options à l'utilisateur
        System.out.println("Choisissez une promotion :");
        for (int option : promotions.keySet()) {
            System.out.println(option + ": " + promotions.get(option).getSimpleName());
        }

        // Lire le choix de l'utilisateur
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        scanner.close();
        // Vérifier si le choix est valide
        if (!promotions.containsKey(choix)) {
            System.out.println("Choix invalide !");
            return;
        }


        // Obtenir la classe correspondante
        Class<? extends Piece> nouvellePieceClasse = promotions.get(choix);

        try {
            // Obtenir le constructeur de la nouvelle classe
            Constructor<? extends Piece> constructeur = nouvellePieceClasse.getConstructor(
                int.class, int.class, int.class, int.class, String.class, Plateau.class
            );

            // Créer une instance de la nouvelle pièce
            Piece nouvellePiece = constructeur.newInstance(
                piece.getPositionXinit(),
                piece.getPositionYinit(),
                piece.getPositionX(),
                piece.getPositionY(),
                piece.getCouleur(),
                plateau
            );

            // Remplacer l'ancienne pièce par la nouvelle sur le plateau
            plateau.placerPiece(nouvellePiece, x, y);

            System.out.println("La pièce a été promue en " + nouvellePiece.getName() + " !");
            if (nouvellePiece instanceof regle_Piece) 
                {
                    ((regle_Piece)nouvellePiece).afficherCoordsPrenable(x, y);
                }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la promotion de la pièce : " + e.getMessage());
        }

    }

    public static Plateau initJeu(int taillePlateau) {
        Plateau plateau = new Plateau(taillePlateau);
    
        // Initialisation des pièces en fonction de la taille du plateau
        switch (taillePlateau) {
            case 8: // Plateau standard 8x8
                ajouterPions(plateau, taillePlateau);
                ajouterTours(plateau, taillePlateau);
                ajouterCavaliers(plateau, taillePlateau);
                ajouterFous(plateau, taillePlateau);
                ajouterDames(plateau, taillePlateau);
                ajouterRois(plateau, taillePlateau);
                break;
    
            case 7: // Plateau 7x7 (sans les dames)
                ajouterPions(plateau, taillePlateau);
                ajouterTours(plateau, taillePlateau);
                ajouterCavaliers(plateau, taillePlateau);
                ajouterFous(plateau, taillePlateau);
                ajouterRois(plateau, taillePlateau);
                break;
    
            case 6: // Plateau 6x6 (sans les fous)
                ajouterPions(plateau, taillePlateau);
                ajouterTours(plateau, taillePlateau);
                ajouterCavaliers(plateau, taillePlateau);
                ajouterDames(plateau, taillePlateau);
                ajouterRois(plateau, taillePlateau);
                break;
    
            case 5: // Plateau 5x5 (sans les fous et sans les dames)
                ajouterPions(plateau, taillePlateau);
                ajouterTours(plateau, taillePlateau);
                ajouterCavaliers(plateau, taillePlateau);
                ajouterRois(plateau, taillePlateau);
                break;
    
            case 4: // Plateau 4x4 (sans les cavaliers et les fous)
                ajouterPions(plateau, taillePlateau);
                ajouterTours(plateau, taillePlateau);
                ajouterDames(plateau, taillePlateau);
                ajouterRois(plateau, taillePlateau);
                break;
    
            default:
                System.out.println("Taille de plateau non prise en charge.");
                break;
        }
    
        // Remplir le plateau avec les pièces
        plateau.initPlateau();
        System.out.println("Jeu initialisé avec un plateau de taille " + taillePlateau + "x" + taillePlateau + ".");
        return plateau;
    }
    
    // Méthodes pour ajouter les pièces en fonction des tailles
    public static void ajouterPions(Plateau plateau, int taille) {
        int ligneBlancs = taille - 2;
        int ligneNoirs = 1;
        for (int i = 0; i < taille; i++) {
            plateau.ajouterPieceBlanche(new Pion(ligneBlancs, i, ligneBlancs, i, "BLANC", plateau));
            plateau.ajouterPieceNoire(new Pion(ligneNoirs, i, ligneNoirs, i, "NOIR", plateau));
        }
    }
    
    public static void ajouterTours(Plateau plateau, int taille) {
        plateau.ajouterPieceBlanche(new Tour(taille - 1, 0, taille - 1, 0, "BLANC", plateau));
        plateau.ajouterPieceBlanche(new Tour(taille - 1, taille - 1, taille - 1, taille - 1, "BLANC", plateau));
        plateau.ajouterPieceNoire(new Tour(0, 0, 0, 0, "NOIR", plateau));
        plateau.ajouterPieceNoire(new Tour(0, taille - 1, 0, taille - 1, "NOIR", plateau));
    }
    
    public static void ajouterCavaliers(Plateau plateau, int taille) {
        if (taille >= 5) {
            plateau.ajouterPieceBlanche(new Cavalier(taille - 1, 1, taille - 1, 1, "BLANC", plateau));
            plateau.ajouterPieceBlanche(new Cavalier(taille - 1, taille - 2, taille - 1, taille - 2, "BLANC", plateau));
            plateau.ajouterPieceNoire(new Cavalier(0, 1, 0, 1, "NOIR", plateau));
            plateau.ajouterPieceNoire(new Cavalier(0, taille - 2, 0, taille - 2, "NOIR", plateau));
        }
    }
    
    public static void ajouterFous(Plateau plateau, int taille) {
        if (taille >= 6) {
            plateau.ajouterPieceBlanche(new Fou(taille - 1, 2, taille - 1, 2, "BLANC", plateau));
            plateau.ajouterPieceBlanche(new Fou(taille - 1, taille - 3, taille - 1, taille - 3, "BLANC", plateau));
            plateau.ajouterPieceNoire(new Fou(0, 2, 0, 2, "NOIR", plateau));
            plateau.ajouterPieceNoire(new Fou(0, taille - 3, 0, taille - 3, "NOIR", plateau));
        }
    }
    
    public static void ajouterDames(Plateau plateau, int taille) {
        if (taille % 2 == 0) 
        { // Vérifie si la taille est paire
            plateau.ajouterPieceBlanche(new Dame(taille - 1, (taille/2) -1, taille - 1, (taille/2) -1, "BLANC", plateau));
            plateau.ajouterPieceNoire(new Dame(0, (taille/2) -1, 0, (taille/2) -1, "NOIR", plateau));
        }
    }
    
    public static void ajouterRois(Plateau plateau, int taille) {
        int colonneRoi = taille / 2;
        plateau.ajouterPieceBlanche(new Roi(taille - 1, colonneRoi, taille - 1, colonneRoi, "BLANC", plateau));
        plateau.ajouterPieceNoire(new Roi(0, colonneRoi, 0, colonneRoi, "NOIR", plateau));
    }
    
    public static Plateau InitJeu8Dames(int taille) {
        Plateau plateau = new Plateau(taille);
        
        // Ligne des dames
        int ligneDamesBlancs = taille - 1; // Dernière ligne pour les Blancs
        int ligneDamesNoirs = 0; // Première ligne pour les Noirs
    
        // Ligne des pions
        int lignePionsBlancs = taille - 2; // Avant-dernière ligne pour les Blancs
        int lignePionsNoirs = 1; // Deuxième ligne pour les Noirs
    
        // Placement des dames
        for (int i = 0; i < taille; i++) {
            plateau.ajouterPieceBlanche(new Dame(ligneDamesBlancs, i, ligneDamesBlancs, i, "BLANC", plateau));
            plateau.ajouterPieceNoire(new Dame(ligneDamesNoirs, i, ligneDamesNoirs, i, "NOIR", plateau));
        }
    
        // Placement des pions
        for (int i = 0; i < taille; i++) {
            plateau.ajouterPieceBlanche(new Pion(lignePionsBlancs, i, lignePionsBlancs, i, "BLANC", plateau));
            plateau.ajouterPieceNoire(new Pion(lignePionsNoirs, i, lignePionsNoirs, i, "NOIR", plateau));
        }
        plateau.initPlateau();
        return plateau;
    }



    
    public static boolean FinDuJeu(Plateau plateau, String couleur)
    {
        ArrayList<Piece> listePieces = plateau.getPlateauPiece();
        boolean pion = true, dame = true;

        for (Piece piece : listePieces) {
            if (piece.getCouleur().equals(couleur))
            {
                if(piece.getName().equals("PION")) 
                {
                    pion = false; // L'équipe possède encore un pion
                }
                else if(piece.getName().equals("DAME"))
                {
                    dame = false; // L'équipe possède encore une dame
                }
            }
            
        }

    return dame || pion;
    }
}