private static final int SIZE = 8; // Taille du plateau (8x8)
public class piece extends Plateau{
    String name;        //nom de la piece
    String Couleur;      //couleur de la piece                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
    int positionX; // coordonnée horizontale
    int positionY;  // coordonnée verticale
    //constructeur
    public piece(String name, int positionX,int positionY,String Couleur)
    {
        this.name  = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.Couleur = Couleur;
    }
    //fonction qui permet le deplacement de la pièce
    public void deplacementPiece(int positionX,int positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;

    }

      //fonction qui permet la prise de pièces
    public void PrisePiece(int positionX,int positionY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
         
    }

    public String Getname()
    {
        return this.name;
    }
re
    public void Setname(String name)
    {
        this.name = name;
    }

    public String GetCouleur()
    {
        return this.Couleur;
    }

    public void SetCouleur(String Couleur)
    {
        this.Couleur = Couleur;
    }

    public int GetPositionX()
    {
        return this.positionX;
    }

    public void SetPositionX(int PositionX)
    {
        this.positionX=PositionX;re
    }

    public int GetPositionY()
    {
        return this.positionX;
    }

    public void SetPositionY(int PositionY)
    {
        this.positionY = PositionY;
    }
    
}
