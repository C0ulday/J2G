


public class Chrono {
    private int minutes;
    private int secondes;


    public Chrono(int m, int s){
      
        minutes = m;
        secondes = s;

    }
    public int getMinutes(){
        return minutes;
    }

    public int getSecondes(){
        return secondes;
    }

    public void setMinutes(int m){
        minutes = m;
    }
    
    public void setSecondes(int s){
        secondes = s;
    }

    public String toString(){

		String contenu = "";

		if(minutes < 10){
			contenu += "0"+minutes;
		}else{
			contenu = contenu + minutes + ":";
		}
		
		if(secondes < 10){
			contenu += "0"+secondes;
		}else{
			contenu += secondes;
		}
		return contenu;
	}
}
