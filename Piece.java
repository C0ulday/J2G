
import java.util.List;
import java.util.ArrayList;
import java.util.List;
public class Piece extends Plateau{
    String name;        //nom de la piece
    String Couleur;      //couleur de la piece                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
    int positionXinit; // coordonnée horizontale
    int positionYinit;  // coordonnée verticale */

    //constructeur
    public Piece(String name, int positionXinit, int positionYinit, String Couleur)
    {
        this.name  = name;
        this.positionXinit = positionXinit;
        this.positionYinit = positionYinit;
        this.Couleur = Couleur;
    }
    public String Getname() 
    {
        return this.name;
    }
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

    public int GetPositionXinit()
    {
        return this.positionXinit;
    }

    public void SetPositionXinit(int PositionXinit)
    {
        this.positionXinit=PositionXinit;
    }

    public int GetPositionYinit()
    {
        return this.positionYinit;
    }

    public void SetPositionYinit(int PositionYinit)
    {
        this.positionYinit = PositionYinit;
    }
    
}
