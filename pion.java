class pion extends piece implements regles
{
    int x;
    int y;
    /*public piece(String name, int positionX,int positionY,String Couleur) */
    public pion(int x,int y,String couleur)
    {
        super("PION",x,y,couleur);
    }

    //vérifie que la position final du pion est légal
    public boolean verifCoup(int coordX,int coordY)
    {
        if(x == coordX && y == coordY +1)
        {
            return true;
        }
        return false;
    }
    // déplacement classique du pion
    public void deplacementPiece(int positionX,int positionY)
    {

    }
    //fonction qui permet de mouvoir le pion de 2 cases au premier coup
    public void PremierdeplacementPiece(int posX_init,int posY_init)
    {
        // Premier coup joué ?
        if (posX_init == GetPositionX() && posY_init == GetPositionY())
        {

            String c =GetCouleur();
            if(c.equals("BLANC"))
            {
                deplacementPiece(posX_init,posY_init + 2);
            }
            else
            {
                deplacementPiece(posX_init,posY_init - 2);
            }
        }

      }
  
    //vérifie si la case ciblé est vide 
    public boolean estVide(int x, int y)
    {
        return true;
    }
    /*prendre une piece
    deplacement en diagonale s'il l'adversaire se trouve en diago
    */
    public void PrisePiece(int x,int y)
    {
        String c =GetCouleur();
        if(c.equals("NOIR"))
        {
            if((non_vide((x+1), (y+1))=="BLANC"))
            {
                deplacementPiece(x+1, y+1);
            }
            if((non_vide((x-1), (y+1))=="BLANC"))
            {
                deplacementPiece(x-1, y+1);
            }
        }
        else
        {
            if((non_vide((x-1), (y-1))=="NOIR"))
            {
                deplacementPiece(x-1, y-1);
            }
            if((non_vide((x+1), (y-1))=="NOIR"))
            {
                deplacementPiece(x+1, y-1);
            }
            
        }
    }
    public String non_vide(int x, int y)
    {
        
        return case(x,y);
        
    }

}