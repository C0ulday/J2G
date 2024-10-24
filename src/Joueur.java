

public class Joueur {

	private boolean estHumain = true;
	private String couleur;
	private String nom;
    
	//Constructeur
	public Joueur(boolean estHumain,String couleur, String nom){

		this.estHumain = estHumain;
        this.couleur = couleur;
		this.nom = nom;
    }
    public String getCouleur(){
        return this.couleur;
    }

	public String getNom(){
        return this.nom;
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