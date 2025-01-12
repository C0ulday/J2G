import java.util.ArrayList;

public  interface regle_Piece {  // notre interface à repecter pour créer une pièce

    // liste des commandes obligatoire à remplir pour créer une pièce

    // fonction calculant tous les coups jouables sur le plateau
    // Entrée : coordonnées de la pièce
    // Sortie : liste de coup possible selon les déplacement de la pièce

    public ArrayList<coordonnee> casesPossibles_Jouable(int xactu, int yactu);





}  
 
