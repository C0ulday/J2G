public interface regles {
    //fonction qui permet la prise de piece
    //congenial-succotash-g47qq6wx9vx9fwv9w.github.dev/c void PrisePiece(int positionX,int positionY);
    
    //fonction qui permet la prise de piece
	public void deplacementPiece(int positionX,int positionY);
    //fonction qui permet le premier déplacmement de la piece
    public void PremierdeplacementPiece(int posX_init,int posY_init);
    public void PrisePiece(int x,int y);
    // Verifie si le coup joué est bon
    public boolean verifCoup(int coordX,int coordY);
     /* Regarde si une piece est presente dans une case
     * @param x Abscisse de la case
     * @param y Ordonnee de la case
     * @return Vrai si elle est vide
     */
    public String non_vide(int x, int y);
    
    /* */
}