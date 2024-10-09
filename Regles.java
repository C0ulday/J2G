public interface regles {
    //fonction qui permet la prise de piece
    public void PrisePiece(int positionX,int positionY);
    
    //fonction qui permet la prise de piece
	public boolean deplacementPiece(int positionX,int positionY);

    //fonction qui permet le premier déplacmement de la piece
    public boolean PremierdeplacementPiece(int positionX,int positionY);
  
    // Verifie si le coup joué est bon
    public boolean verifCoup(int coordX,int coordY);

     /* Regarde si une piece est presente dans une case
     * @param x Abscisse de la case
     * @param y Ordonnee de la case
     * @return Vrai si elle est vide
     */
    public boolean estVide(int x, int y);
}
