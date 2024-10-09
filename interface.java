import java.io.BufferedWriter;
import java.io.FileWriter;

 /* 
 * Travail sur la communication entre plusieurs clients et un serveur
 * pour la gestion du J2G
 * 
 */


public class void Client {

     String name;
     int id; // identifiant du client
     int token; // jeton qui indique s'il est autorisé ou non à accéder dans le fichier


    public class Client(int id, int token, String name){

        this.id = id;
        this.token = 0;
        this.name = name;
    }

    public boolean READY(String pathFichier){

        /**
         * Signifie que le client est prêt
         * Il envoie alors ses infos au serveur et attend
         */

        // Va écrire dans le fichier ses infos et dire au serveur qu'il est prêt

        try{   
            // Ecriture dans fichier
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathFichier, true));
            writer.write(id + "");
            writer.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier " + fichier + " est introuvable.");
        }
    }


}


    public static void main(string[] args){
        Client g = new Client(1, 87, "mame");
        g.READY("C:\\Users\\galas\\Desktop\\connect.JSON");
    }