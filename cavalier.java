class cavalier extends piece implements regles
{
    int x;
    int y;
    /*constructeur */
    public cavalier(int x,int y,String couleur){
        super("CAVALIER",x,y,couleur);
    }
    
    public boolean verifCoup(int coordX,int coordY)
    {
        if(x == coordX+1 && y == coordY +2)
        {
            return true;
        }
        if(x == coordX-1 && y == coordY +2)
        {
            return true;
        }
        if(x == coordX-2 && y == coordY -1)
        {
            return true;
        }
        if(x == coordX -2 && y == coordY + 1)
        {
            return true;
        }
        if(x == coordX +2 && y == coordY -1)
        {
            return true;
        }
        if(x == coordX +2 && y == coordY + 1)
        {
            return true;
        }
        if(x == coordX+1 && y == coordY -2)
        {
            return true;
        }
        if(x == coordX-1 && y == coordY -2)
        {
            return true;
        }
        return false;
    }
    	
    public void deplacementPiece(int positionX,int positionY)
    {
        /* */
    }
    
    
    //fonction qui permet le premier déplacmement de la piece
    public boolean PremierdeplacementPiece(int positionX,int positionY)
    {
       
    }

    public boolean estVide(int x, int y)
    {
        return true;
    }
}
