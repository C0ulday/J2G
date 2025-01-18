public class Joueur
{
    	/**
	 * Defini si le Joueur est humain ou non
	 */

	protected boolean estHumain = true;
	
	/**
	 * Nom du joueur
	 */
	private String Nom;
	private Chrono chrono;

    
	//Constructeur
	public Joueur(String c,Chrono chrono)
    {
        this.Nom = c;
		this.chrono = new Chrono(0,10,0);
    }
    public String getNom()
    {
        return this.Nom;
    }

    	/**
	 * Retourne si le joueur est humain
	 * @return True si il l'est sinon false
	 */
	public boolean estHumain(){
		return estHumain;
	}

	/** 
	 * Defini si le Joueur est humain
	 * @param b
	 */
	public void setHumain(boolean b){
		estHumain = b;
	}
    
}