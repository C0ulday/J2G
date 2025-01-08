class pion extends piece implements regles
{
    int x;
    int y;
    boolean premierCoup = true; // Ajout d'un flag pour vérifier si c'est le premier coup

    public pion(int x,int y,String couleur)
    {
        super("PION",x,y,couleur);
    }

    //vérifie que la position final du pion est légal 
    @Override
    public boolean verifCoup(int coordX,int coordY)
    {
        if(x == coordX && y == coordY +1)
        {
            return true;
        }
        return false;
    }
    // déplacement classique du pion
    @Override
    public void deplacementPiece(int positionX,int positionY)
    {
        if(estVide(positionX, positionY))
        {
            super.deplacementPiece(positionX, positionY); // Developper dans piece
            premierCoup = false; // Premier coup joué, on désactive ce flag si le déplacement est valide
        }
    }
    //fonction qui permet de mouvoir le pion de 2 cases au premier coup
    @Override
    public void PremierdeplacementPiece(int posX_init,int posY_init)
    {
        // Premier coup joué ?
        if (premierCoup)
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
            premierCoup = false; // Premier coup joué, on désactive ce flag
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
    @Override
    public void PrisePiece(int x,int y)
    {
        String c =GetCouleur();
        if(c.equals("NOIR"))
        {
            if((non_vide((x+1), (y+1))=="BLANC"))
            {
                deplacementPiece(x+1, y+1);
            }
            else if((non_vide((x-1), (y+1))=="BLANC"))
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
            else if((non_vide((x+1), (y-1))=="NOIR"))
            {
                deplacementPiece(x+1, y-1);
            }
            
        }
    }
    @Override


}